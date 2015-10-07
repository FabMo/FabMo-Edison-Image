DESCRIPTION="The FabMo Engine Service"
LICENSE = "Apache2"

SRC_URI = "git://github.com/Fabmo/FabMo-Engine.git;protocol=https"
SRCREV = "${AUTOREV}"

#LIC_FILES_CHKSUM = "file://LICENSE;md5=ea398a763463b76b18da15f013c0c531"
LIC_FILES_CHKSUM = "file://LICENSE;md5=175792518e4ac015ab6696d16c4f607e"

S = "${WORKDIR}/git"

DEPENDS = "nodejs-native"
RDEPENDS_${PN} = "python"

do_compile() {
    # changing the home directory to the working directory, the .npmrc will be created in this directory
    export HOME=${WORKDIR}

    # does not build dev packages
    npm config set dev false

    # access npm registry using http
    npm set strict-ssl false
    npm config set registry http://registry.npmjs.org/

    # configure http proxy if neccessary
    if [ -n "${http_proxy}" ]; then
        npm config set proxy ${http_proxy}
    fi
    if [ -n "${HTTP_PROXY}" ]; then
        npm config set proxy ${HTTP_PROXY}
    fi

    # configure cache to be in working directory
    npm set cache ${WORKDIR}/npm_cache

    # clear local cache prior to each compile
    npm cache clear

    # compile and install  node modules in source directory
    npm --arch=${TARGET_ARCH} --verbose install
}

do_install() {
    install -d ${D}${systemd_unitdir}/system
    cp -r ${S} ${D}/fabmo
    install -m 0644 ${S}/conf/fabmo.service ${D}${systemd_unitdir}/system/
}

inherit systemd

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "fabmo.service"

FILES_${PN} = "${systemd_unitdir}/system \
               /fabmo"

PACKAGES = "${PN}"
