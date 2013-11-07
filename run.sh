#!/bin/bash
vagrant destroy -f
vagrant up
vagrant ssh -c 'cd /vagrant/browsertest && mvn test'
vagrant package 
