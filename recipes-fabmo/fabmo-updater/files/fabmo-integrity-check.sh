#!/bin/bash

ENGINE_TOKEN=/fabmo/engine/install_token
UPDATER_TOKEN=/fabmo/updater/install_token

if [ ! -e $UPDATER_TOKEN ] || [ ! -e $ENGINE_TOKEN ] ; then
	echo "FabMo installation integrity has been compromised.  Peforming a factory reset."
	/usr/lib/fabmo/factory_reset.sh startup
else
	echo "FabMo installation integrity is OK."
fi
