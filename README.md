FabMo-Edison-Image
----------------------
THIS BRANCH IS EXPERIMENTAL !

This is a yocto layer for furnishing the working parts of FabMo on the intel edison.


#Build Instructions
Run `make` - This will fetch the intel sources, and prepare the yocto linux build environment to run.  The Makefile will provide instructions for actually conducting the build, once the environment setup is complete.

#Warning
you may need to add this lines to the local.conf file located in out/current/conf/ :
```
CPPFLAGS_append_pn-ncurses-native = " -P"
CPPFLAGS_append_pn-subversion-native = " -P"
CPPFLAGS_append_pn-binutils-native = " -w"
CFLAGS_append_pn-cross-localedef-native = " -fgnu89-inline"
```

# Dependencies
Dependencies for the build enviornment are below.  This has only been tested on Ubuntu.

## Ubuntu
- makeinfo (`sudo apt-get install texinfo`)
- chrpath (`sudo apt-get install chrpath`)
