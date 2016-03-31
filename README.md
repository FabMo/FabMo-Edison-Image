FabMo-Edison-Image
----------------------

This is a yocto layer for furnishing the working parts of FabMo on the intel edison.


#Build Instruction
- Download the [yocto distribution sources](http://downloadmirror.intel.com/25028/eng/edison-src-ww25.5-15.tgz)
- Untar it
- `cd edison src`
- `git clone git://git.openembedded.org/meta-openembedded`
- `git clone git://github.com/imyller/meta-nodejs.git`
- `git clone https://github.com/FabMo/FabMo-Edison-Image.git`
- `make setup`
- `cd out/current`
- `source poky/oe-init-build-env`
- edit ./conf/bblayers.conf
- add theses lines to the BBLAYERS section :
  - `/home/user/FabMo-Build/edison-src/meta-nodejs \`
  - `/home/user/FabMo-Build/edison-src/FabMo-Edison-Image \`
- `bitbake fabmo-image`
- Pray for it to build.

#Dependencies
##Ubuntu
- makeinfo (`sudo apt-get install texinfo`)
- chrpath (`sudo apt-get install chrpath`)
