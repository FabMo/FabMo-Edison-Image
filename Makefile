BASEDIR = $(shell git rev-parse --show-toplevel)
SRCDIR = $(BASEDIR)/edison-src
OUTDIR = $(SRCDIR)/out/current
BBLAYERS = $(OUTDIR)/build/conf/bblayers.conf
MAKEFILE = $(OUTDIR)/build/Makefile
BUILDTOOLS = $(OUTDIR)/build/build-tools

SH = bash

all: $(BBLAYERS) $(MAKEFILE) $(BUILDTOOLS) build instructions
 
instructions:
	@echo ""
	@echo "The Intel Edison build environment has been prepared to create the FabMo image."
	@echo ""
	@echo "To build the image:"
	@echo ""
	@echo "cd edison-src/out/current"
	@echo "source poky/oe-init-build-env"
	@echo "make"
	@echo ""
	@echo "Good Luck!"
	@echo ""

$(BUILDTOOLS): intel-setup
	cp -R support/build-tools $(BUILDTOOLS)

$(MAKEFILE): intel-setup
	cp support/Makefile $(BUILDTOOLS)
	
$(BBLAYERS): intel-setup
	awk '{print $0}/.*meta-arduino.*/{print "  $(SRCDIR)/meta-nodejs \\\n  $(SRCDIR)/FabMo-Edison-Image \\"}' $(BBLAYERS) > .dl/bblayers.conf
	cp .dl/bblayers.conf $(BBLAYERS)
 
intel-setup: | edison-src/meta-openembedded edison-src/meta-nodejs edison-src/FabMo-Edison-Image
	cd $(SRCDIR); \
	make setup; \
	cd $(OUTDIR); \
	$(SH) -c '. poky/oe-init-build-env'	

edison-src:
	mkdir -p .dl
	wget -P .dl http://downloadmirror.intel.com/25028/eng/edison-src-ww25.5-15.tgz
	tar -xvzf .dl/edison-src-ww25.5-15.tgz

edison-src/meta-openembedded: | edison-src
	cd edison-src; \
	git clone git://git.openembedded.org/meta-openembedded

edison-src/meta-nodejs: | edison-src
	cd edison-src; \
	git clone git://github.com/imyller/meta-nodejs.git

edison-src/FabMo-Edison-Image: | edison-src
	cd edison-src; \
	ln -s `git rev-parse --show-toplevel` ./FabMo-Edison-Image

clean:
	rm -rf .dl edison-src

build:
	ln -s ./edison-src/out/current/build ./build

.PHONY: clean instructions
