DESCRIPTION="The FabMo Updater Service"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${THISDIR}/LICENSE;md5=175792518e4ac015ab6696d16c4f607e"

PV = "1.4.65"

DEPENDS = "dbus-glib expat"
RDEPENDS_${PN} = "git bash nodejs-npm bossa factory-reset"

do_fetch() {
	cd ${WORKDIR}
	PACKAGE=`node ${THISDIR}/files/latest.js`
	wget ${PACKAGE} -O package.fmp
	echo ${PACKAGE}
        #tar -xvzf package.fmp
}

do_unpack() {
	echo "Unpacking"
	cd ${WORKDIR}
	rm -rf ./fabmo-updater-files
	mkdir ./fabmo-updater-files
        tar -xvzf package.fmp
        tar -xvzf files.tar.gz -C ./fabmo-updater-files 
	find ./fabmo-updater-files -name '.debug' -print0 | xargs -0 -n 1 rm -rf 
}

do_clean() {
	rm -rf ${WORKDIR}/*
}

do_install() {

    # Create install directories
    install -d ${D}/usr/lib/fabmo
    install -d ${D}/fabmo
    install -d ${D}/fabmo/updater
    install -d ${D}/fabmo/site
    install -d ${D}${bindir}

    # Install the updater just downloaded 
    #tar -xzf ${WORKDIR}/files.tar.gz -C ${D}/fabmo/updater
    cp -r ${WORKDIR}/fabmo-updater-files/* ${D}/fabmo/updater

    # Install systemd unit files
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${D}/fabmo/updater/files/fabmo-updater.service ${D}${systemd_unitdir}/system/
    install -m 0644 ${THISDIR}/files/fabmo-updater-update.service ${D}${systemd_unitdir}/system/

    # Install factory reset/AP Mode button management stuff 
    tar -cvjf ${D}/usr/lib/fabmo/updater-factory.tar.bz2 -C ${D}/fabmo/updater --exclude='*install_token' .
    install -m 0755 ${THISDIR}/files/factory_reset.sh ${D}/usr/lib/fabmo/    
    install -m 0755 ${THISDIR}/files/updater_update.sh ${D}/usr/lib/fabmo/    
    install -m 0755 ${THISDIR}/files/enter_ap_mode.sh ${D}/usr/lib/fabmo/    
    install -m 0755 ${THISDIR}/files/fabmo-factory-reset ${D}${bindir}    
    
    # Install the firmware update fmu into the /usr/lib/fabmo directory
    install -d ${D}/usr/lib/fabmo/fmus    
    install -m 0755 ${THISDIR}/files/01-firmware-update.fmu ${D}/usr/lib/fabmo/fmus
    
    # Link the /opt/fabmo configuration path to the home directory (where configs are actually stored)
    install -d ${D}/opt
    install -d ${D}/home/fabmo
    chown -R fabmo ${D}/home/fabmo
    lnr ${D}/home/fabmo ${D}/opt/fabmo

}

inherit systemd

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "fabmo-updater.service"

PACKAGES = "${PN}"
INSANE_SKIP_${PN} += "debug-files"

inherit useradd

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} - "-d /home/fabmo -r -s /bin/bash fabmo"

FILES_${PN} = "${systemd_unitdir}/system /fabmo /opt /usr /home ${bindir}"
