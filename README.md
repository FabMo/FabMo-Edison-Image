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
- `bitbake fabmo-image`
- Pray for it to build.

#Dependencies
##Ubuntu
- makeinfo
- chrpath
