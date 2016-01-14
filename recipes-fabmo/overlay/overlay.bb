DESCRIPTION="Aufs overlay tools for FabMo on the Intel Edison"
LICENSE = "Apache-2.0"
FILESEXTRAPATHS_prepend := "${THISDIR}/files:" 
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=175792518e4ac015ab6696d16c4f607e"

SRC_URI += "file://overlay"
SRC_URI += "file://LICENSE"

DEPENDS = "bash"
RDEPENDS_${PN} = "bash"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/overlay ${D}${bindir}
}

FILES_${PN} = "${bindir}"

PACKAGES = "${PN}"
