DESCRIPTION="Install config for Handibot on top of FabMo"
LICENSE = "Apache-2.0"
FILESEXTRAPATHS_prepend := "${THISDIR}/files:" 
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=175792518e4ac015ab6696d16c4f607e"

SRC_URI += "file://LICENSE"
SRC_URI += "file://handibot-config-install.sh"

DEPENDS = "bash fabmo-engine"
RDEPENDS_${PN} = "git bash post-install"

do_install() {
    install -d ${D}/sbin    
    install -m 0755 ${WORKDIR}/handibot-config-install.sh ${D}/sbin
}

FILES_${PN} = "/sbin/handibot-config-install.sh"
