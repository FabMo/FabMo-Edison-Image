DESCRIPTION="Site installation files for Handibot"
LICENSE = "Apache-2.0"
FILESEXTRAPATHS_prepend := "${THISDIR}/files:" 
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=175792518e4ac015ab6696d16c4f607e"

SRC_URI += "file://LICENSE"
SRC_URI += "file://01-firmware-update.fmu"
SRC_URI += "file://02-handibot-production.fmu"

DEPENDS = "bash"
RDEPENDS_${PN} = "bash"

do_install() {
    install -d ${D}/usr/lib/fabmo/fmus    
    install -m 0755 ${WORKDIR}/01-firmware-update.fmu ${D}/usr/lib/fabmo/fmus
    install -m 0755 ${WORKDIR}/02-handibot-production.fmu ${D}/usr/lib/fabmo/fmus
}

FILES_${PN} = "/usr/lib/fabmo/fmus/01-firmware-update.fmu /usr/lib/fabmo/fmus/02-handibot-production.fmu"
