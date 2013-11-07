#!/bin/bash
vagrant destroy -f
vagrant up
vagrant ssh -c 'cd /vagrant/browsertest && mvn test'
rm -f package.box && vagrant package 
