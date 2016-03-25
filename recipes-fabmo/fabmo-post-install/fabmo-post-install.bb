DESCRIPTION="Post-install script for FabMo on the intel edison"
LICENSE = "Apache-2.0"
FILESEXTRAPATHS_prepend := "${THISDIR}/files:" 
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=175792518e4ac015ab6696d16c4f607e"

SRC_URI += "file://LICENSE"
SRC_URI += "file://fabmo-post-install.sh"

DEPENDS = "bash"
RDEPENDS_${PN} = "bash post-install"

do_install() {
    install -d ${D}/sbin    
    install -m 0755 ${WORKDIR}/fabmo-post-install.sh ${D}/sbin
}

FILES_${PN} = "/sbin/fabmo-post-install.sh"
