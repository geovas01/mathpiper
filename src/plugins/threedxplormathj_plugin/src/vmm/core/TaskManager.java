/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import java.util.ArrayList;
import java.util.Collection;

import vmm.functions.EvalStack;


/**
 * Provides parallization for a collection of tasks, where each task is an
 * object of type Runnable.  The tasks can be run by a pool of threads,
 * where the size of the pool can be specified in the TaskManager constructor.  
 * The default is for the number ofthreads to be equal to the number of processors.
 * <p>There are three ways to run a collection of tasks: {@link #executeAndWait(Collection)}
 * will run all the task in a collection of Runnable objects and will return only
 * when all the tasks have finished.  {@link #executeAsync(Collection)} will run
 * the tasks asynchronously.  The return value of this method is an object of
 * type {@link TaskManager.Job}; this "job" object can be used to get status infomation
 * about the job, to cancel the job, and to wait for the job to finish.
 * {@link #createJob()} also returns an object of type TaskManager.Job, but in this
 * case the job does not initially have any tasks to do.  Tasks can be added by
 * calling {@link TaskManager.Job#add(Runnable)}.  After all tasks that are part of
 * the job have been added, {@link TaskManager.Job#close()} must be called to
 * indicate that the job is complete.  (Note that it is <b>not</b> possible to
 * add additional tasks to a job that has been created using {@link #executeAsync(Collection)}.)
 */
public class TaskManager {
	
	/**
	 * Represents a job that consists of the execution of a number of tasks.
	 * It is not possible to create an object of this class directly.  Objects
	 * of type TaskManager.Job are returned by {@link TaskManager#executeAsync(Collection)}
	 * and {@link TaskManager#createJob()}.  The tasks that are part of a job will
	 * be exectued by the TaskManager that created the job, using the pool of
	 * threads in that TaskManager.  A job can start executing as soon as it has
	 * been created and the first task has been added.
	 * @see TaskManager
	 */
	public static class Job {
		private final TaskManager owner;
		private final ArrayList<Runnable> tasks;
		private volatile boolean closed;
		private volatile boolean finished;
		private volatile boolean canceled;
		private int nextTask;
		private volatile int finishedTaskCount;
		private volatile int failedTaskCount;
		private final ArrayList<Runnable> waitingFinishedTasks;
		private final ArrayList<Runnable> waitingFailedTasks;
		private Job(TaskManager owner, Collection<? extends Runnable> tasks) {
			this.owner = owner;
			if (tasks == null)
				this.tasks = new ArrayList<Runnable>();
			else
				this.tasks = new ArrayList<Runnable>(tasks);
			for (int i = this.tasks.size() - 1; i >= 0; i--)
				if (this.tasks.get(i) == null)
					this.tasks.remove(i);
			waitingFinishedTasks = new ArrayList<Runnable>();
			waitingFailedTasks = new ArrayList<Runnable>();
		}
		private void finish(Runnable task) {
			synchronized(owner) {
				if (finished)
					return;
				finishedTaskCount++;
				waitingFinishedTasks.add(task);
				if (closed && finishedTaskCount + failedTaskCount == tasks.size()) {
					finished = true;
					owner.finish(this);
				}
			}
		}
		private void fail(Runnable task) {
			synchronized(owner) {
				if (finished)
					return;
				failedTaskCount++;
				waitingFailedTasks.add(task);
				if (closed && finishedTaskCount + failedTaskCount == tasks.size()) {
					finished = true;
					owner.finish(this);
				}
			}
		}
		private Runnable nextTask() {
			synchronized(owner) {
	 			if (finished || nextTask >= tasks.size())
					return null;
				else
					return tasks.get(nextTask++);
			}
		}
		/**
		 * Add a task to this job.  The job will not finish until all the tasks that have
		 * been added to the job have finished (successfully or because of an exception).  Also,
		 * the job must be closed before it can finish.
		 * Note that tasks cannot be added to a job that has is "closed".  See {@link #close()}.
		 * @param task the task that is to be added to this job.  A null value is ignored.
		 * @throws IllegalStateException if the job has already been closed.
		 */
		public void add(Runnable task) {
			if (task == null)
				return;
			if (closed)
				throw new IllegalStateException("Can't add a new task to a job after the job has been closed.");
			synchronized(owner) {
				tasks.add(task);
				owner.notifyAll();
			}
		}
		/**
		 * "Close" this job, making it possible for the job to complete.  Closing a job also makes
		 * it impossible to add new tasks to the job.  A job that was created using
		 * {@link TaskManager#executeAsync(Collection)} is already closed when it is returned by
		 * that method.  A job that was created using {@link TaskManager#createJob()} must
		 * be closed, or it will be impossible for that job to finish; the job should be closed
		 * by calling this method after all the tasks that are part of the job have been added.
		 */
		synchronized public void close() {
			if (closed)
				return;
			closed = true;
			if (finishedTaskCount + failedTaskCount == tasks.size()) {
				finished = true;
				owner.finish(this);
			}
		}
		/**
		 * Tells the fraction of tasks that have been added to this job that have been completed.
		 * @return a number between 0 and 1 obtained by dividing the number of completed tasks by
		 * the number of tasks that have been added.  If no tasks have been added, the return value
		 * is 1.  Note that the fractionDone can go down, if more tasks are added to the job.
		 */
		public double fractionDone() {
			if (tasks.size() == 0)
				return 1;
			else
				return (double)(finishedTaskCount + failedTaskCount)/tasks.size();
		}
		/**
		 * Returns the number of tasks in this job that have been completed successfully.
		 */
		public int finishedTaskCount() {
			return finishedTaskCount;
		}
		/**
		 * Returns the number of tasks in this job that have been terminated because of an exception.
		 */
		public int failedTaskCount() {
			return failedTaskCount;
		}
		/**
		 * Returns the number of tasks that have been added to this job.
		 */
		public int totalTaskCount() {
			return tasks.size();
		}
		/**
		 * Cancel the job.  Tasks that have not yet been started will not be discarded; however,
		 * tasks that are in progress can run to completion and might finish after this method
		 * returns.  This method can be called to cancel a job even if that job has not yet been
		 * closed.
		 */
		public void cancel() {
			synchronized(owner) {
				finished = true;
				canceled = true;
				closed = true;
				owner.finish(this);
				owner.notifyAll();
			}
		}
		/**
		 * Tells whether the job is finished.  A job is finished either when all the tasks
		 * that are part of the job are done or when the job has been canceled.
		 */
		public boolean isFinished() {
			return finished;
		}
		/**
		 * Tells whether hte job has been canceled.  A job can be canceled by calling
		 * {@link #cancel()}.
		 */
		public boolean isCanceled() {
			return canceled;
		}
		/**
		 * Returns an array that contains tasks from this job that have completed successfully.
		 * If this method has been called previously, only the newly completed tasks, since the
		 * last call, are returned.  The return value can be an empty array, if there are no
		 * newly completed tasks, but the return value is never null.  This method can be
		 * used to retreive completed tasks for further processing.
		 */
		public Runnable[] finishedTasks() {
			synchronized(owner) {
				if (waitingFinishedTasks.size() == 0)
					return new Runnable[0];
				Runnable[] tasks = new Runnable[waitingFinishedTasks.size()];
				waitingFinishedTasks.toArray(tasks);
				waitingFinishedTasks.clear();
				return tasks;
			}
		}
		/**
		 * Returns an array that contains tasks from this job that have terminated because of
		 * an Error or Exception.  If this method has been called previously, only the newly
		 * completed tasks, since the last call, are returned.  The return value is non-null
		 * but can be empty.
		 */
		public Runnable[] failedTasks() {
			synchronized(owner) {
				if (waitingFailedTasks.size() == 0)
					return new Runnable[0];
				Runnable[] tasks = new Runnable[waitingFailedTasks.size()];
				waitingFailedTasks.toArray(tasks);
				waitingFailedTasks.clear();
				return tasks;
			}
		}
		/**
		 * Waits either a specified amount of time or indefinitely for this job to finish.
		 * The method will return only after the job completes or after the specified timeout
		 * if the job does not complete within that time.
		 * The return value tells whether or not the job has completed.  If the job is
		 * already complete when this method is called, it returns immedialtely.
		 * @param timeoutMilliseconds the maximum time to wait for the job to finish. A value
		 * of 0 (or less) means to wait as long as it takes for the job to finish.
		 * @return true if the job has finished, false if not.  Note that if the argument is
		 * less than or equal to 0, then the return value has to be true.
		 */
		public boolean await(int timeoutMilliseconds) {
			synchronized(owner) {
				if (finished)
					return true;
				try {
					if (timeoutMilliseconds <= 0)
						owner.wait();
					else
						owner.wait(timeoutMilliseconds);
				}
				catch (InterruptedException e) {
				}
				return finished;
			}
		}
	}
	
	private final ThreadPool threadPool;
	private boolean shutDown;
	private ArrayList<Job> activeJobs;
	private int nextJobForTask;
	
	/**
	 * Create a TaskManager that will use a pool of threads with one thread per available processor.
	 * @see #TaskManager(int)
	 */
	public TaskManager() {
		this(0);
	}
	
	/**
	 * Create a TaskManager that will use a pool of threads with a specified number of threads.
	 * The threads are used to execute "jobs", where a job consists of a collection of
	 * Runnable objects.  Note that even a thread pool with just one thread can be useful
	 * for asynchronous execution.
	 * @param threadPoolSize the number of thread to be used.  If the value is 0 (or less),
	 * then the number of threads will be equal to the number of available processors.
	 */
	public TaskManager(int threadPoolSize) {
		if (threadPoolSize <= 0)
			threadPoolSize = Runtime.getRuntime().availableProcessors();
		activeJobs = new ArrayList<Job>();
		threadPool = new ThreadPool(this, threadPoolSize);
	}
	
	/**
	 * This method should be called before discarding the TaskManager.  Any jobs that
	 * have not been completed are cancled (using {@link TaskManager.Job#cancel()}).
	 * Then the threads in the thread pool are allowed to die.  It is not possible
	 * to add new jobs to a TaskManager after the TaskManager has been shut down.
	 */
	synchronized public void shutDown() {
		shutDown = true;
		for (Job job : activeJobs)
			job.cancel();
		notifyAll();
	}
	
	/**
	 * Returns the number of threads that will be used in the thread pool.
	 * The value was set in the constructor and does not change.
	 * The return value is a positive integer.
	 */
	public int getThreadPoolSize() {
		return threadPool.getSize();
	}
	
	/**
	 * Executes all the tasks in a collection of tasks.  This method does not
	 * return until all the tasks have finished, either by terminating
	 * normally or by throwing an exception.  If the thread pool size is
	 * 1, all the tasks are executed in the calling thread, without the
	 * use of any additional threads.  If the thread pool size is greater
	 * than 1, all the threads in the pool are used to execute the tasks, 
	 * resulting in some parallelization.
	 * @see #getThreadPoolSize()
	 * @param tasks the tasks to be performed.  Must be non-null.  Each task in the collection
	 * is an object of type Runnable.  Null entries in the collection are ignored.  (Note that the
	 * actual parameter can be (for example) an ArrayList declared as of type
	 * ArrayList&lt;Runnable&gt; or ArrayList&lt;Type&gt; where Type is a class
	 * that implements the Runnable interface.
	 * @throws IllegalStateException if this method is called after {@link #shutDown()} has been called.
	 * @throws NullPointerException if the argument is null
	 */
	public void executeAndWait(Collection<? extends Runnable> tasks) {
		if (shutDown)
			throw new IllegalStateException("Can't execute tasks after shutdown.");
		if (tasks == null)
			throw new NullPointerException("The collection of tasks can't be null.");
		if (getThreadPoolSize() == 1) {
			for (Runnable task : tasks) {
				try {
					if (task != null)
						task.run();
				}
				finally {  // if one task fails, continue with the next task
				}
			}
		}
		else {
			Job job = executeAsync(tasks);
			job.await(0);
		}
	}
	
	
	/**
	 * Creates a job to execute a specified collection of tasks, and starts working
	 * on the job.  This method returns immedialtely, and the execution of the tasks
	 * procedes asynchronously. The tasks are executed using the TaskManager's thread pool.  
	 * @param tasks a non-null collection of tasks to be executed.  Each task is an object
	 * of that implements the Runnable interface.  Null tasks are ignored.
	 * @return a "job" object that can be used to get status information about the job and
	 * to wait for the job to be completed.  It is not possible to add additional tasks
	 * to this job.
	 * @throws IllegalStateException if this method is called after {@link #shutDown()} has been called.
	 * @throws NullPointerException if the argument is null
	 */
	synchronized public Job executeAsync(Collection<? extends Runnable> tasks) {
		if (shutDown)
			throw new IllegalStateException("Can't execute tasks after shutdown.");
		if (tasks == null)
			throw new NullPointerException("The collection of tasks can't be null.");
		Job job = new Job(this,tasks);
		job.close();
		activeJobs.add(job);
		notifyAll();
		return job;
	}
	
	
	/**
	 * Creates a "job" to which a collection of tasks can be added.  See {@link TaskManager.Job#add(Runnable)}.
	 * The job must be "closed," using {@link TaskManager.Job#close()} after all the tasks have been added,
	 * or the job will never complete.
	 * @return the job object.  This can be used to add tasks to the job, to get status information about
	 * the job, and to wait for the job to complete.
	 * @throws IllegalStateException if this method is called after {@link #shutDown()} has been called.
	 */
	synchronized public Job createJob() {
		if (shutDown)
			throw new IllegalStateException("Can't execute tasks after shutdown.");
		Job job = new Job(this,null);
		activeJobs.add(job);
		return job;
	}
	
	
	/**
	 * Tells whether this TaskManager has at least one job that has not yet finished.  Note that the
	 * TaskManager might not really be doing anyting, if none of the jobs have tasks that
	 * need to be performed.
	 */
	synchronized public boolean busy() {
		return activeJobs.size() > 0;
	}
	
	
	synchronized private void finish(Job job) {  // called by a job when it is finished, so the job can be removed from this task manager.
		if (!shutDown) {
			int jobnum = activeJobs.indexOf(job);
			if (jobnum == -1)
				return;
			activeJobs.remove(jobnum);
			if (jobnum < nextJobForTask)
				nextJobForTask--;
		}
		notifyAll();
	}
	
	synchronized private Object[] nextTask() {  // goes through the list of active jobs looking for a task to do; can block if no task is available
		Runnable task = null;
		Job job = null;
		while (task == null && !shutDown) {
			if (activeJobs.size() > 0) {
				if (nextJobForTask >= activeJobs.size())
					nextJobForTask = 0;
				int jobnum = nextJobForTask;
				do {
					job = activeJobs.get(jobnum);
					task = job.nextTask();
					jobnum++;
					if (jobnum >= activeJobs.size())
						jobnum = 0;
				} while (task == null && jobnum != nextJobForTask);
			}
			if (task == null) {
				try {
					wait();
				}
				catch (InterruptedException e) {
				}
			}
		}
		if (shutDown)
			return null;
		nextJobForTask++;
		return new Object[] { task, job };
	}
	
	private static class ThreadPool {
		final Worker[] pool;
		final TaskManager owner;
		boolean shutDown;
		ThreadPool(TaskManager owner, int poolSize) {
			this.owner = owner;
			pool = new Worker[poolSize];
			int priority = Thread.currentThread().getPriority();
			for (int i = 0; i < poolSize; i++) {
				pool[i] = new Worker();
				pool[i].setDaemon(true);
				try {
					pool[i].setPriority(priority-1);
				}
				catch (Exception e) {
				}
				pool[i].start();
			}
		}
		int getSize() {
			return pool.length;
		}
		class Worker extends Thread {
			public void run() {
				Job job;
				try {
					while (true) {
						Runnable task;
						Object[] taskinfo = owner.nextTask();
						if (taskinfo == null)
							break;
						task = (Runnable)taskinfo[0];
						job = (Job)taskinfo[1];
						boolean ok = true;
						try {
							task.run();
						}
						catch (Throwable e) {
							ok = false;
						}
						finally {
							if (ok)
								job.finish(task);
							else
								job.fail(task);
						}
					}
				}
				finally {
					EvalStack.perThreadRelease(this);
				}
			}
		}
	}
	

}
