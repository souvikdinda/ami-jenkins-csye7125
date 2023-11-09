variable "aws_region" {
  type    = string
  default = "us-east-1"
}

variable "ssh_username" {
  type    = string
  default = "ubuntu"
}

variable "root_account_id" {
  type    = string
  default = "191434387957"
}

variable "vaishnavi_root_account_id" {
  type    = string
  default = "779915759129"
}

variable GH_USERNAME {
  type    = string
  default = ""
}

variable QUAY_USERNAME {
  type    = string
  default = ""
}

variable GH_CREDS {
  type    = string
  default = ""
}

variable QUAY_CREDS {
  type    = string
  default = ""
}

packer {
  required_plugins {
    amazon = {
      version = " >= 1.0.8"
      source  = "github.com/hashicorp/amazon"
    }
  }
}

# Source block
source "amazon-ebs" "jenkins-ami" {
  region          = "${var.aws_region}"
  ssh_username    = "${var.ssh_username}"
  ami_name        = "JenkinsAMI_${formatdate("YYYYMMDDhhmmss", timestamp())}"
  ami_description = "AMI for Jenkins"
  ami_regions = [
    "${var.aws_region}"
  ]
  ami_users = ["${var.root_account_id}", "${var.vaishnavi_root_account_id}", "674687836138"]

  aws_polling {
    delay_seconds = 120
    max_attempts  = 50
  }

  source_ami_filter {
    filters = {
      virtualization-type = "hvm"
      name                = "ubuntu/images/hvm-ssd/ubuntu-jammy-22.04-amd64-server-20230516"
      root-device-type    = "ebs"
    }
    owners      = ["amazon"]
    most_recent = true
  }
  instance_type = "t2.micro"

  launch_block_device_mappings {
    delete_on_termination = true
    device_name           = "/dev/sda1"
    volume_size           = 8
    volume_type           = "gp2"
  }
}

build {
  sources = ["source.amazon-ebs.jenkins-ami"]

  provisioner "file" {
    source      = "./jenkins/Dockerfile"
    destination = "/tmp/Dockerfile"
  }

  provisioner "file" {
    source      = "./jenkins/casc.yaml"
    destination = "/tmp/casc.yaml"
  }

  provisioner "file" {
    source      = "./jenkins/multibranch-pipeline.groovy"
    destination = "/tmp/multibranch-pipeline.groovy"
  }

  provisioner "file" {
    source      = "./jenkins/plugins.txt"
    destination = "/tmp/plugins.txt"
  }

  provisioner "file" {
    source      = "./jenkins/script.sh"
    destination = "/tmp/script.sh"
  
  }

  provisioner "shell" {
    script = "./packer/shell-script.sh"
    environment_vars = ["GH_USERNAME=${ var.GH_USERNAME }", "GH_CREDS=${ var.GH_CREDS }", "QUAY_USERNAME=${ var.QUAY_USERNAME }", "QUAY_CREDS=${ var.QUAY_CREDS }"]
  }
}