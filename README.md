Test problem
============
This repository demonstrates several technologies working together:
* Vagrant (tested on Vagrant 1.3.5 but should work on earlier versions)
* Chef-solo with recipes cookbook (used built-in Chef-solo version)
* JUnit (to write test cases)
* Maven (to compile and run tests)
* Selenium Grid (hub + nodes) to attach JUnit to different browsers

How to run
----------
Simply execute run.sh script and it will do the following:
* Shutdown any existing Vagrant VMs
* Initialize a clean VM from precise32 (Ubuntu 12.04 LTS Precise Pangolin 32 bit) image
* Setup required Selenium and Maven environment software via Chef-solo recipes
* Run two Selenium tests for latest Chrome and Firefox browser versions and create PNG screenshots in ./screenshots/ directory.
* Gracefully shutdown the VM and create a snapshot in Vagrant format (./package.box file)
By default the script outputs log entries to STDOUT. So you can redirect output to file using standard Linux redirection operator: ./run.sh > logfile.log
