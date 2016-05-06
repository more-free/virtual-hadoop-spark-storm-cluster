## notes
Vagrantfile is for starting the virtual cluster : 

follow this guide "how to install a virtual apache hadoop cluster with vagrant and cloudera manager" to install a local 4-nodes cluster,
https://blog.cloudera.com/blog/2014/06/how-to-install-a-virtual-apache-hadoop-cluster-with-vagrant-and-cloudera-manager/
it includes spark, storm, hdfs, hbase, etc.

test env :  /Users/morefree/Development/play/hadoop/virtual-hadoop-cluster
login : vagrant ssh master; vagrant ssh slave[1-3]
hdfs cmd :  sudo -u hdfs hadoop fs -ls /

trouble shooting : virtual boxes are not accessible from host. solution : restart host and vagrant reload --provision can resolve that (if doesn't work then may consider re-install it).
make sure on the host running "ifconfig" doesn't show virtualbox net interface before running "vagrant reload --provision"


# A working virtual Hadoop cluster

With these files you can setup and provision a locally running, virtual Hadoop cluster in real distributed fashion for trying out Hadoop and related technologies. It runs the latest Cloudera Hadoop distribution: **CDH5**. It also allows you to practise the use of [Cloudera Manager](http://www.cloudera.com/content/cloudera/en/products-and-services/cloudera-enterprise/cloudera-manager.html) for installing the Hadoop stack. If you're looking for a fully automated install, without user intervention, look elsewhere. I specifically made this with the goal of creating an environment ideally suited for Cloudera Manager to do its job. This gives you the freedom to actually install the services you want, and change the configuration how you see fit.

This README describes how to get the cluster with Cloudera Manager up and running. For more detailed instructions on how to install the whole Hadoop stack on that, you can use [this guide](http://dandydev.net/blog/installing-virtual-hadoop-cluster).

## Specs

The cluster conists of 4 nodes:

* Master node with 4GB of RAM (Running the NameNode, Hue, ResourceManager etc. after installing the Hadoop services)
* 3 slaves with 2GB of RAM each (Running DataNodes)

As you can see, you'll need at least 10GB of free RAM to run this. If you have less, you can try to remove one machine from the Vagrantfile. This will lead to worse performance though!

## Usage

Depending on the hardware of your computer, installation will probably take between 15 and 25 minutes.

First install [VirtualBox](https://www.virtualbox.org/) and [Vagrant](http://www.vagrantup.com/).

Install the Vagrant [Hostmanager plugin](https://github.com/smdahlen/vagrant-hostmanager)

```bash
$ vagrant plugin install vagrant-hostmanager
```

Clone this repository.

```bash
$ git clone https://github.com/DandyDev/virtual-hadoop-cluster.git
```

Provision the bare cluster. It will ask you to enter your password, so it can modify your `/etc/hosts` file for easy access in your browser. It uses the Vagrant Hostmanager plugin to do this.

```bash
$ cd virtual-hadoop-cluster
$ vagrant up
```

Go to the [Cloudera Manager web console](http://vm-cluster-node1:7180) and follow the installation instructions. For more detailed instructions on how to do that, you can use [this guide](http://dandydev.net/blog/installing-virtual-hadoop-cluster).

**Done!** Have fun with your Hadoop cluster.
