all: intel-setup

intel-setup: | edison-src/meta-openembedded edison-src/meta-nodejs edison-src/FabMo-Edison-Image
	cd edison-src; \
	make setup;

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

.PHONY: clean
	
#make-fabmo
#- Untar it
#- `cd edison src`
#- `git clone git://git.openembedded.org/meta-openembedded`
#- `git clone git://github.com/imyller/meta-nodejs.git`
#- `git clone https://github.com/FabMo/FabMo-Edison-Image.git`
#- `make setup`
#- `cd out/current`
#- `source poky/oe-init-build-env`
#- edit ./conf/bblayers.conf
#- add theses lines to the BBLAYERS section :
#  - `/home/user/FabMo-Build/edison-src/meta-nodejs \`
#  - `/home/user/FabMo-Build/edison-src/FabMo-Edison-Image \`
#- `bitbake fabmo-image`
#- Pray for it to build.

#Dependencies
##Ubuntu
#- makeinfo (`sudo apt-get install texinfo`)
#- chrpath (`sudo apt-get install chrpath`)
