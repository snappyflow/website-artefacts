<!-- Copyright 2020 MapleLabs -->
<!-- Author: Aanchal Sathyanarayanan (aanchal.sathyanarayanan@maplelabs.com) -->
<!-- Description: README file for Single Click deployment on Ubuntu. -->

# Single Click Deployment Steps for Ubuntu

We will be using [ansible](https://docs.ansible.com/ansible/latest/installation_guide/intro_installation.html) for single click deployment of the entire application along with all the necessary installations.

# Prerequisite and Ansible Installation
    sudo apt update
    sudo apt install git
    sudo apt-add-repository ppa:ansible/ansible
Press ENTER when prompted to accept the PPA addition.
    
    sudo apt install ansible
Press Y and then press ENTER where ever required.


(Optional) To verify if ansible is installed: 

    ansible --version

# Cloning the repository
    cd /opt
Cloning the Javasamples repository:

    sudo git clone https://github.com/pramurthy/javasamples.git
(Optional) To verify if git is installed: 

    git --version   


# Running ansible Playbook 
    cd /opt/javasamples/ansible
    
    ansible-playbook -i hosts playbook.yml

# Modifying the hosts file
This step is **optional** and used only in the cases when deployment happens on multiple VM instances. The hosts file has groups according to which the multiple VMs IP will be divided, as per the need.
You need to be logged in as root user to perform this operation.

    cd /opt/javasamples/ansible
    
    vi hosts

If it is preferred that JMeter should not be installed then after opening the hosts file make the variable **jmeter_install = false**

# Modifying the default_vars.yml file
This file contains the varibles that can be modified, **if required**, by the user.

There are variables which define the path and directories for many installations. It can be changed if needed.

If SF Agent is installed and SF Trace functionality is needed, then the SFTRACE_AGENT variable can be made true.

To open and edit the default_vars.yml:

    cd /opt/javasamples/ansible
    
    vi default_vars.yml