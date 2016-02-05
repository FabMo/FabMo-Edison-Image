DESCRIPTION = "BOSSA - The Basic Open Source SAM BA Loader"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=94411054a7f6921218ab9c05b6b4b15b"
PV = "0.0.1"

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/shumatech/BOSSA.git;protocol=git;branch=arduino"

DEPENDS = "readline"

S = "${WORKDIR}/git/"

do_compile() {
	make -e bin/bossac bin/bossash
}

do_install() {
    install -d ${D}${bindir}
    cp ${S}bin/bossac ${D}${bindir}
    cp ${S}bin/bossash ${D}${bindir}
}

FILES_${PN} = "${bindir}"
