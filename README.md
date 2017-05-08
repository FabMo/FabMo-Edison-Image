FabMo-Edison-Image
----------------------

This is a yocto layer for furnishing the working parts of FabMo on the intel edison.


#Build Instructions
Run `make` - This will fetch the intel sources, and prepare the yocto linux build environment to run.  The Makefile will provide instructions for actually conducting the build, once the environment setup is complete.

# Dependencies
Dependencies for the build enviornment are below.  This has only been tested on Ubuntu.

## Ubuntu
- makeinfo (`sudo apt-get install texinfo`)
- chrpath (`sudo apt-get install chrpath`)
