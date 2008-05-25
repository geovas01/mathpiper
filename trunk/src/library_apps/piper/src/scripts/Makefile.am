## Makefile.am -- Process this file with automake to produce Makefile.in

# The script files are listed in two separate variables, SCRIPTFILES1 and SCRIPTFILES2
# because of Sun computers  

SCRIPTFILES1 = 	        										    \
	standard.pi standard.pi.def stdopers.pi piperinit.pi					    \
	array.rep/code.pi array.rep/code.pi.def 							    \
	assoc.rep/code.pi assoc.rep/code.pi.def 							    \
	c_form.rep/code.pi c_form.rep/code.pi.def 							    \
	complex.rep/code.pi complex.rep/code.pi.def complex.rep/om.pi 					    \
	constants.rep/code.pi constants.rep/code.pi.def  constants.rep/om.pi 				    \
	controlflow.rep/code.pi controlflow.rep/code.pi.def 						    \
	deffunc.rep/code.pi deffunc.rep/code.pi.def 							    \
	deriv.rep/code.pi deriv.rep/code.pi.def 							    \
	example.rep/code.pi example.rep/code.pi.def 							    \
	factors.rep/code.pi factors.rep/code.pi.def 							    \
	factors.rep/binaryfactors.pi factors.rep/binaryfactors.pi.def 					    \
	functional.rep/code.pi functional.rep/code.pi.def functional.rep/om.pi				    \
	html.rep/code.pi html.rep/code.pi.def 								    \
	integrate.rep/code.pi integrate.rep/code.pi.def integrate.rep/om.pi   \
	io.rep/code.pi io.rep/code.pi.def io.rep/print.pi io.rep/formula.pi io.rep/errors.pi 		    \
	io.rep/defaultprint.pi io.rep/defaultprint.pi.def 						    \
	limit.rep/code.pi limit.rep/code.pi.def limit.rep/om.pi						    \
	linalg.rep/code.pi linalg.rep/code.pi.def 							    \
	lists.rep/code.pi lists.rep/code.pi.def 							    \
	lists.rep/scopestack.pi lists.rep/scopestack.pi.def 						    \
	localrules.rep/code.pi localrules.rep/code.pi.def 						    \
	logic.rep/code.pi logic.rep/code.pi.def logic.rep/om.pi 					    \
	multivar.rep/code.pi multivar.rep/code.pi.def multivar.rep/makemulti.pi  			    \
	multivar.rep/sparsetree.pi multivar.rep/sparsetree.pi.def multivar.rep/sparsenomial.pi	 	    \
	newly.rep/code.pi newly.rep/code.pi.def 							    \
	numbers.rep/code.pi numbers.rep/code.pi.def numbers.rep/om.pi					    \
	numbers.rep/nthroot.pi numbers.rep/nthroot.pi.def                                                   \
	odesolver.rep/code.pi odesolver.rep/code.pi.def 						    \
	openmath.rep/code.pi openmath.rep/code.pi.def 							    \
	orthopoly.rep/code.pi orthopoly.rep/code.pi.def 						    \
	padic.rep/code.pi padic.rep/code.pi.def 							    \
	patterns.rep/code.pi patterns.rep/code.pi.def 							    \
	plots.rep/code.pi plots.rep/code.pi.def plots.rep/backends-2d.pi plots.rep/backends-3d.pi           \
        plots.rep/plot2d.pi.def plots.rep/plot3d.pi.def plots.rep/plot2d.pi plots.rep/plot3d.pi             \
	predicates.rep/code.pi predicates.rep/code.pi.def 						    \
	pslq.rep/code.pi pslq.rep/code.pi.def 								    \
	rabinmiller.rep/code.pi rabinmiller.rep/code.pi.def 						    \
	radsimp.rep/code.pi radsimp.rep/code.pi.def 							    \
	random.rep/code.pi random.rep/code.pi.def 							    \
	simplify.rep/code.pi simplify.rep/code.pi.def simplify.rep/factorial.pi simplify.rep/factorial.pi.def 							    \
	solve.rep/code.pi solve.rep/code.pi.def                                                		    \
	debug.rep/code.pi debug.rep/code.pi.def			  					    \
	specfunc.rep/bessel.pi specfunc.rep/bessel.pi.def specfunc.rep/bernou.pi specfunc.rep/bernou.pi.def \
	specfunc.rep/code.pi specfunc.rep/code.pi.def specfunc.rep/gammaconst.pi specfunc.rep/gammaconst.pi.def \
	specfunc.rep/gamma.pi specfunc.rep/gamma.pi.def specfunc.rep/zeta.pi specfunc.rep/zeta.pi.def specfunc.rep/om.pi  \
	statistics.rep/hypothesystest.pi statistics.rep/incompletegamma.pi.def	 			    \
	statistics.rep/regression.pi.def statistics.rep/hypothesystest.pi.def 				    \
	statistics.rep/randomtest.pi statistics.rep/statistics.pi 					    \
	statistics.rep/incompletegamma.pi statistics.rep/regression.pi	 				    \
	statistics.rep/statistics.pi.def 								    \
	statistics.rep/distributions.pi statistics.rep/distributions.pi.def 




#        test.pi    test.pi.def


SCRIPTFILES2 = \
	stats.rep/code.pi stats.rep/code.pi.def 							    \
	stdfuncs.rep/code.pi stdfuncs.rep/code.pi.def stdfuncs.rep/om.pi stdfuncs.rep/numerical.pi 	    \
	stdfuncs.rep/numerical.pi.def stdfuncs.rep/nummethods.pi stdfuncs.rep/nummethods.pi.def 	    \
	stubs.rep/code.pi stubs.rep/code.pi.def stubs.rep/om.pi stdfuncs.rep/elemfuncs.pi                   \
	substitute.rep/code.pi substitute.rep/code.pi.def stdfuncs.rep/elemfuncs.pi.def 		    \
	sums.rep/code.pi sums.rep/code.pi.def sums.rep/taylor.pi sums.rep/taylor.pi.def   sums.rep/taylor3.pi sums.rep/taylor3.pi.def   sums.rep/om.pi  \
	tensor.rep/code.pi tensor.rep/code.pi.def 							    \
	testers.rep/code.pi testers.rep/code.pi.def 							    \
	texform.rep/code.pi texform.rep/code.pi.def 							    \
	trigsimp.rep/code.pi trigsimp.rep/code.pi.def 							    \
	univar.rep/code.pi univar.rep/code.pi.def univar.rep/sturm.pi univar.rep/sturm.pi.def               \
	univar.rep/Cyclotomic.pi  univar.rep/Cyclotomic.pi.def 						    \
	univar.rep/sparse.pi  univar.rep/sparse.pi.def 	                                                    \
        probability.rep/code.pi  probability.rep/code.pi.def                                                \
	numbers.rep/NumberTheory.pi numbers.rep/NumberTheory.pi.def                                         \
	numbers.rep/GaussianIntegers.pi numbers.rep/GaussianIntegers.pi.def				    \
	transforms.rep/code.pi transforms.rep/code.pi.def \
  base.rep/math.pi base.rep/math.pi.def \
	stdarith.pi stdarith.pi.def 

EXAMPLE_SCRIPTFILES = \
	examples/ABIN.pi examples/pi.pi \
	examples/findsum.pi examples/wordproblems.pi \
	examples/queens.pi examples/benchmark.pi examples/MinimumSpanningTree.pi \
	examples/benchmark2.pi examples/benchbuild.pi  examples/goldbach.pi examples/series.pi

GENERATED_SCRIPTFILES = \
	packages.pi corefunctions.pi

#CLEANFILES = $(GENERATED_SCRIPTFILES)
#noinst_SCRIPTS = $(GENERATED_SCRIPTFILES)

# All the above script files need to be installed in $(scriptdir).
# Furthermore, we do not want to strip the directory: "univar.rep/code.pi"
# needs to be installed as "$(scriptdir)/univar.rep/code.pi", and not as
# "$(scriptdir)/code.pi". Automake 1.5 has a nice nobase_ prefix to achieve
# this, but we want to be compatible with version 1.4. So we copied the
# following code from the code generated by Automake 1.5.

ALLSCRIPTS = $(SCRIPTFILES1) $(SCRIPTFILES2) $(EXAMPLE_SCRIPTFILES) $(GENERATED_SCRIPTFILES)

install-data-local: $(ALLSCRIPTS)
	@$(NORMAL_INSTALL)
	$(mkinstalldirs) $(DESTDIR)$(scriptdir)
	@list='$(ALLSCRIPTS)'; for p in $$list; do \
	  dir=`echo "$$p" | sed -e 's,/[^/]*$$,,'`; \
	  if test "$$dir" != "$$p" && test "$$dir" != "."; then \
	    $(mkinstalldirs) "$(DESTDIR)$(scriptdir)/$$dir"; \
	  fi; \
	  f="`echo $$p|sed '$(transform)'`"; \
	  if test -f $$p; then \
	    echo " $(INSTALL_DATA) $$p $(DESTDIR)$(scriptdir)/$$f"; \
	    $(INSTALL_DATA) $$p $(DESTDIR)$(scriptdir)/$$f; \
	  elif test -f $(srcdir)/$$p; then \
	    echo " $(INSTALL_DATA) $(srcdir)/$$p $(DESTDIR)$(scriptdir)/$$f"; \
	    $(INSTALL_DATA) $(srcdir)/$$p $(DESTDIR)$(scriptdir)/$$f; \
	  else :; fi; \
	done

uninstall-local:
	@$(NORMAL_UNINSTALL)
	@list='$(ALLSCRIPTS)'; for p in $$list; do \
	  f="`echo $$p|sed '$(transform)'`"; \
	  echo " rm -f $(DESTDIR)$(scriptdir)/$$f"; \
	  rm -f $(DESTDIR)$(scriptdir)/$$f; \
	done

## Scripts are not automatically distributed, so force this

EXTRA_DIST = $(ALLSCRIPTS) maketest 

# Automatically generate a full list of all .def files

all-local: $(srcdir)/packages.pi $(srcdir)/corefunctions.pi

$(srcdir)/packages.pi: $(srcdir)/Makefile.am
	@echo "Creating list of packages in $@...";                  
	@echo "/// This file is generated by a script."        > $@ 
	@echo "/// It lists all {.def} files in the library."  >> $@
	@echo "Defun(DefFileList,{}) {"                             >> $@
	@echo $(SCRIPTFILES1) $(SCRIPTFILES2)               \
           | tr -s '[ \t]' '[\n*]' | grep '\.def$$' | sort  \
	   | uniq | sed 's/^/"/;s/$$/",/;s/\.def//'	       >> $@
	@echo "};"                                            >> $@

$(srcdir)/corefunctions.pi: $(top_srcdir)/src/corefunctions.h $(top_srcdir)/src/pipermain.cpp
	(cd ../src && make gencorefunctions$(EXEEXT))
	../src/gencorefunctions > $(srcdir)/corefunctions.pi



check:
	@(for i in $(ALLSCRIPTS); do echo $$i; done) | sort > scripts-listed-in-makefile.txt
	@find $(srcdir)/../scripts \( -name \*.pi -o -name \*.pi.def \) -print \
	   | sed -e 's,.*scripts/,,' | sort > contents-of-tree.txt
	@echo "Comparing scripts listed in the Makefile against the contents of the tree"
	@if diff scripts-listed-in-makefile.txt contents-of-tree.txt; \
	   then echo " ... ok"; \
	 else \
	   false; \
	 fi
