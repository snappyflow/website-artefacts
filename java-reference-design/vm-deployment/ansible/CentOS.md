<!-- Copyright 2020 MapleLabs -->
<!-- Author: Aanchal Sathyanarayanan (aanchal.sathyanarayanan@maplelabs.com) -->
<!-- Description: README file for Single Click deployment on CentOS. -->

# Single Click Deployment Steps for CentOS

We will be using [ansible](https://docs.ansible.com/ansible/latest/installation_guide/intro_installation.html) for single click deployment of the entire application along with all the necessary installations.
# Prerequisite and Ansible Installation
    sudo yum update
    sudo yum install git
    sudo yum install epel-release
To confirm the installation, press Y and then press ENTER.
    
    sudo yum install ansible
Press Y and then press ENTER where ever required.

(Optional) To verify if ansible is installed: 

    ansible --version

# Cloning the repository
    cd /opt

Cloning the Javasamples repository:

    sudo git clone https://github.com/snappyflow/website-artefacts.git
(Optional) To verify if git is installed: 

    git --version   


# Running ansible Playbook 
    cd /opt/website-artefacts/java-reference-design/vm-deployment/ansible/
    
    ansible-playbook -i hosts playbook.yml

# Modifying the hosts file
This step is **optional** and used only in the cases when deployment happens on multiple VM instances. The hosts file has groups according to which the multiple VMs IP will be divided, as per the need.
You need to be logged in as root user to perform this operation.

    cd /opt/website-artefacts/java-reference-design/vm-deployment/ansible/
    
    vi hosts
    
If you want continous load generation using JMeter, open host.ini file and change below value

    jmeter_continous_load_generater = True

# Modifying the default_vars.yml file
This file contains the varibles that can be modified, **if required**, by the user.

There are variables which define the path and directories for many installations. It can be changed if needed.

If SF Agent is installed and SF Trace functionality is needed, then the SFTRACE_AGENT variable can be made true.

To open and edit the default_vars.yml:

    cd /opt/website-artefacts/java-reference-design/vm-deployment/ansible/
    
    vi default_vars.yml