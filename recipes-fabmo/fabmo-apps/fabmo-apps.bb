DESCRIPTION="Apps for fabmo installation"
LICENSE = "Apache-2.0"
FILESEXTRAPATHS_prepend := "${THISDIR}/files:" 
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=175792518e4ac015ab6696d16c4f607e"

SRC_URI += "file://LICENSE"

DEPENDS = "bash"
RDEPENDS_${PN} = "bash"

S = "${WORKDIR}"

do_fetch() {
	cd ${S}
	wget https://github.com/FabMo/fabmo-productionapp-app/releases/download/v1.0.0/FINAL.CHECK._v1.0.0.fma
}

do_clean() {
	rm -rf ${S}
}

do_install() {
    install -d ${D}/home/fabmo/apps    
    install -m 0755 ${S}/FINAL.CHECK._v1.0.0.fma ${D}/home/fabmo/apps
}

FILES_${PN} = "/home/fabmo/apps/FINAL.CHECK._v1.0.0.fma"
