DESCRIPTION = "Services related to factory reset, that initiate it both by button press, or failure to pass a software integrity check"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
  
FILESEXTRAPATHS_prepend := "${THISDIR}/files/:"

RDEPENDS_${PN} = "bash curl"

inherit systemd

SYSTEMD_SERVICE_${PN} = "factory-reset-monitor.service fabmo-integrity-check.service"
SYSTEMD_AUTO_ENABLE = "enable"

SRC_URI = "file://pwr-button-handler.c"
SRC_URI += "file://factory-reset.service"
SRC_URI += "file://factory-reset-monitor.service"
SRC_URI += "file://fabmo-integrity-check.service"
SRC_URI += "file://fabmo-integrity-check.sh"

S = "${WORKDIR}"

do_compile() {
        ${CC} $CFLAGS -DNDEBUG -o pwr_button_handler pwr-button-handler.c
}

do_install() {
        install -d ${D}${bindir}
        install -m 0755 pwr_button_handler ${D}${bindir}

        # Copy service file
        install -d ${D}/${systemd_unitdir}/system
        install -c -m 644 ${WORKDIR}/factory-reset-monitor.service ${D}/${systemd_unitdir}/system
        install -c -m 644 ${WORKDIR}/factory-reset.service ${D}/${systemd_unitdir}/system
        install -c -m 644 ${WORKDIR}/fabmo-integrity-check.service ${D}/${systemd_unitdir}/system
 
    	install -d ${D}/usr/lib/fabmo
        install -c -m 755 ${WORKDIR}/fabmo-integrity-check.sh ${D}/usr/lib/fabmo/
}

FILES_${PN} += "${systemd_unitdir}/system /usr ${bindir}/pwr_button_handler"
