// Bean Sheet
// Copyright (C) 2004-2007 Alexey Zinger
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.

package zinger.bsheet.module;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.jar.*;

import zinger.bsheet.*;
import zinger.util.*;

public class ModuleFactory
{
	public static final ModuleFactory INSTANCE = new ModuleFactory();

	protected final FilenameFilter propertiesFileFilter = new WildCardFilenameFilter("*.properties");

	private List modules;

	protected ModuleFactory()
	{
	}

	public synchronized List getModules()
	{
		if(this.modules == null)
		{
			List moduleList;

			File[] moduleDescriptors = this.getModuleDescriptors();
			if(moduleDescriptors == null)
			{
				moduleList = new ArrayList(0);
			}
			else
			{
				moduleList = new ArrayList(moduleDescriptors.length);
				Module module;
				for(int i = 0; i < moduleDescriptors.length; ++i)
				{
					try
					{
						module = this.loadModule(moduleDescriptors[i]);
						if(module != null)
						{
							moduleList.add(module);
						}
					}
					catch(IOException ex)
					{
						ex.printStackTrace();
					}
					catch(ModuleLoadException ex)
					{
						ex.printStackTrace();
					}
				}
			}
			this.modules = Collections.unmodifiableList(moduleList);
		}
		return this.modules;
	}

	public File[] getModuleDescriptors()
	{
		String moduleDirName = Main.getConstant("module.dir");
		if(moduleDirName == null)
		{
			return null;
		}
		File moduleDir = new File(this.getModuleHomeDir(), moduleDirName);
		return moduleDir.listFiles(this.propertiesFileFilter);
	}

	public File getModuleHomeDir()
	{
		URL url = this.getClass().getClassLoader().getResource("zinger/bsheet/constants.properties");
		if(url == null)
		{
			return null;
		}
		if("jar".equals(url.getProtocol()))
		{
			try
			{
				url = new URL(url.getFile());
			}
			catch(MalformedURLException ex)
			{
				ex.printStackTrace();
				return null;
			}
		}
		String fileName;
		if("file".equals(url.getProtocol()))
		{
			fileName = url.getFile();
			int index = fileName.indexOf("zinger/bsheet");
			if(index > -1)
			{
				fileName = fileName.substring(0, index);
			}
			index = fileName.indexOf('!');
			if(index > -1)
			{
				fileName = fileName.substring(0, index);
			}
		}
		else
		{
			return null;
		}
		File file = new File(fileName);
		if(!file.exists())
		{
			return null;
		}
		if(file.isFile())
		{
			file = file.getParentFile();
		}
		return file;
	}

	public Module loadModule(File modDescriptor) throws IOException, ModuleLoadException
	{
		Properties modConfig = new Properties();
		modConfig.setProperty("mod.home", modDescriptor.getParentFile().getAbsolutePath());
		InputStream is = new FileInputStream(modDescriptor);
		try
		{
			modConfig.load(new BufferedInputStream(is));
		}
		finally
		{
			is.close();
		}

		Util.loadImports(modConfig, Locale.getDefault(), this.getClass().getClassLoader());
		Util.evalAllProperties(modConfig, modConfig);
		return this.loadModule(modConfig);
	}

	public Module loadModule(Properties modConfig) throws ModuleLoadException
	{
		String enabled = modConfig.getProperty("mod.enabled");
		if(enabled != null && "false".equalsIgnoreCase(enabled))
		{
			return null;
		}
		URL[] cpURLs = this.getCPURLs(modConfig);
		Module module = this.loadModule(new URLClassLoader(cpURLs, this.getClass().getClassLoader()), modConfig.getProperty("mod.impl.class"));
		module.init(modConfig);
		return module;
	}

	public Module loadModule(ClassLoader loader, String className) throws ModuleLoadException
	{
		try
		{
			System.out.println("Loading module " + className);
			Class moduleClass = loader.loadClass(className);
			Module module = (Module)moduleClass.newInstance();
			return module;
		}
		catch(InstantiationException ex)
		{
			throw new ModuleLoadException(loader, className, "Could not instantiate module main class.", ex);
		}
		catch(IllegalAccessException ex)
		{
			throw new ModuleLoadException(loader, className, "Illegal access to module main class.", ex);
		}
		catch(ClassNotFoundException ex)
		{
			throw new ModuleLoadException(loader, className, "Module main class not found.", ex);
		}
	}

	/**
	 * @since 0.9.3
	 */
	public URL[] getCPURLs(Properties modConfig) throws ModuleLoadException
	{
		File modHomeDir = new File(modConfig.getProperty("mod.home"));
		String modCP = modConfig.getProperty("mod.class.path");
		String[] modCPArray = modCP.split("\\s");
		URL[] cpURLs = new URL[modCPArray.length];
		File cpFile;
		for(int i = 0; i < modCPArray.length; ++i)
		{
			try
			{
				cpFile = new File(modCPArray[i]);
				if(!cpFile.exists())
				{
					cpFile = new File(modHomeDir, modCPArray[i]);
				}
				cpURLs[i] = cpFile.toURI().toURL();
			}
			catch(MalformedURLException ex) // shouldn't happen
			{
				throw new ModuleLoadException(null, null, "Improper URL for file " + modCPArray[i], ex);
			}
		}
		return cpURLs;
	}
}
