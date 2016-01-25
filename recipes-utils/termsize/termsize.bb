# http://shallowsky.com/blog/hardware/serial-24-line-terminals.html

DESCRIPTION="termsize terminal size detection tool for serial ttys"
LICENSE = "Apache-2.0"
FILESEXTRAPATHS_prepend := "${THISDIR}/files:" 
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=175792518e4ac015ab6696d16c4f607e"


SRC_URI += "file://termsize"
SRC_URI += "file://LICENSE"

DEPENDS = "bash python"
RDEPENDS_${PN} = "bash python"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/termsize ${D}${bindir}
}

FILES_${PN} = "${bindir}"

PACKAGES = "${PN}"
