# FabMo-Edison-Image
This repository contains all the files needed for building the FabMo linux image for the Intel Edison.  The build system for the image is Yocto Linux (OpenEmbedded) which is frozen at an older version (1.7.2) due to Intel's cessation of support for the Edison.  Intel provides a source distribution for that version of yocto that includes all of their packages, patches, and a setup script, which is included in this repository (edison-src-ww25.5-15.tgz) in anticipation it being eventually taken down from Intel's site.  Historically, the yocto sources for the Edison were hosted here [here](http://downloadmirror.intel.com/25028/eng/edison-src-ww25.5-15.tgz)

# Overview
At the top level, this repository represents a "layer" that renders a number of recipes onto the Intel yocto distribution.  There is a README file in each recipe that is worth reading that describes what each recipe is, and how it interacts with other recipes.  Mainly, the FabMo layer is responsible for installing the FabMo Engine, the Updater, and all of the scripts and system files that support those softwares.

# Building the linux image
The main difficulty in building with bitbake/yocto is the *environment* - a very narrow window of linux versions for a host environment are required, and certain tools have to be installed.  The older yocto reference distribution on which the Intel image is based will not build with gcc 5.x so back-revs of the development tools are reuqired to build the image.  To relieve the pain of setting up an environment, configuration for a docker container that performs the build is provided.  You can create the container and perform the build in it with a single command: `make container` which is a target in the `Makefile` at the top level directory of this repository.

# Dockerized build structure
The `docker` folder provides all of the Dockerfiles and a script (`build.sh`) for stringing together a container with a fully configured build environment.  Because the environment is composed of individual images that are built upon one another, the environment is bootstrapped in stages, which reduces the need to restart the *entire* build process if one piece of it fails.  The stages of the build/images are listed below:
 * `edison/ubuntu-build` - Includes the Ubuntu (trusty) base files and all the developer tools that are needed at their appropriate versions.
 * `edison/edison-user` - Configures the user environment in the ubuntu build image - creating a single unpriveleged user `edison` with no password.
 * `edison/edison-source` - Checks out this repository inside the container and prepares the build environment by running the `Makefile` that is in the same directory as this readme. This unpacks the Intel edison source distribution, runs Intel's setup scripts and moves some files around to prepare for the build.
 * `edison/edison-download` - Runs the bitbake `fetchall` step for the FabMo image.  This downloads all the sources needed to build the yocto distribution.  It is an isolated step because it takes a long time, and can fail if network conditions are poor, or if individual sources are unavailable for some reason.
 * `edison/edison-image` - Runs the actual build.  The build is initiated as a part of the container creation script, so if it fails, the container is not actually created.  This is probably not ideal and could use some work.  If successful however, the edison/edison-image tag will point to an image of a completed build environment, with all intermediate build products and output intact.  You can then instantiate this container with `docker run -it edison/edison-image /bin/bash`

# Important files and directories
Yocto is an impossibly large and complex build system.  In lieu of an explanation of how it works, (please see the yocto project documentation for that) a list of important files and directories are provided to give a sense of how to interact with the build.
 * `Makefile` - The makefile in the top level directory is responsible for setting up the build environment.  It unpacks Intel's sources, fetches the appropriate version of the OpenEmbedded build system and several layers that are needed, and copies some files and creates some symbolic links that are needed to make the build work.
 * `support/Makefile` - This makefile is used to actually run the build and process its output.  The setup Makefile described above copies it to the build directory when the environment is configured so that it can be used to initiate the build.
 * `/edison-src/` - This directory is where the intel sources live, unpacked from `edison-src-ww25.5-15.tgz`
 * `/edison-src/out/current/poky/oe-init-build-env.sh` - This is the script that configures the openembedded build environment.  It configures some paths and sets up some environment variables that are needed by the build process.  You must `source` this script (which is different than running it) before attempting to build with `bitbake`
 * `/edison-src/out/current/build` - This is the build directory.  The build takes place here, which can be initiated by running `make` from within this directory, after `source`ing the `oe-init-build-env.sh` described above.
 * `/edison-src/out/current/build/toFlash` - This is the directory where the assembled build products go.  A flashing script is included here, along with all image files.

# Some recipes
To explain the system by way of example, here are some recipes for doing typical build tasks.

### Create the docker container and build the FabMo image
```
make container
```

### Open a shell in the created build container and perform another build
```
# Instantiate the image build container and open a shell inside it
docker run -it edison/edison-image /bin/bash
# Use the OpenEmbedded init script to prepare the shell environment to build the image
source fabmo-edison-image/edison-src/out/current/poky/oe-init-build-env.sh
# The OE setup script puts you in the build directory, so all you have to do is run make
make
```

### Purge your system of all containers and images
```
# This will basically start you from scratch, container-wise.
docker system prune -a
```

