LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
LICENSE = "MIT"


SRC_URI_append = "\
                   file://configure_edison\
                 "
RDEPENDS_${PN}_append = " python-modules"
FILESEXTRAPATHS_append := "${THISDIR}/files:"

do_install_append() {
   install -m 0755 ${WORKDIR}/configure_edison ${D}${bindir}
}
