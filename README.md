边缘计算-树莓派使用教程

树莓派设备上安装 Ubuntu MATE

*   设置 SD 卡

将sd卡插在读卡器上连接到电脑主机后，使用命令mount在最后一行查看自己的设备号,找寻sd卡的挂载目录，如图可以看到此sd卡的挂载目录为/dev/sdb1(若有sdb1,sdb2等，则说明有多个分区)。

`$ mount`

![](https://thundersoft.feishu.cn/space/api/box/stream/download/asynccode/?code=7e44d9f5c68e544e14d9f078faef4de5_8f118824ce50c961_boxcn1YJJjkoLsHmsulmWodtXJg_xFYBF7Zh2phGC4oYDqGGHxUDGtFNCooD)

由于sd卡插上之后会自动mount，所以需要unmout

`$ umount 路径名   (此sd卡的例子为：umount /dev/sdb1）`

使用fdisk删除已存在的分区并创建新分区

`$ sudo fdisk /dev/sdb`

![](https://thundersoft.feishu.cn/space/api/box/stream/download/asynccode/?code=f908ea8b8d8b44de4d49b5dcb65e5071_8f118824ce50c961_boxcnhNoZAVg6dSpHvvHPL6E7Jy_PBssLDQ070k7ue46tTDym3RUdXjUXeu6)

如图所示，输入m，可看到命令获取帮助，之后输入d删除分区（若有多个分区，需要依次删除）。

![](https://thundersoft.feishu.cn/space/api/box/stream/download/asynccode/?code=42a903b6b8626f69e522906df86c5e36_8f118824ce50c961_boxcn9i17O7I9tHdMjPCavv6dwd_9ZmyJXsPR0gccbVIk67LdCOjbdxuLASB)

建议：之后在sd卡内存设定时全部选择默认（敲击回车），系统会自动选择default后显示的内容（也可以先输入n创建新分区，后输入p选择primay分区，后输入1选择第1分区号，后若sd卡的内存是32GB，则输入2048与62333951表示first sector与last sector）。在sd卡内存设定结束后，输入w进行储存。

最后格式化sd卡

`$ mkfs.msdos /dev/sdb1 （若选择了第一分区号，则是mkfs.msdos /dev/sdb1）`

*   下载Ubuntu Mate镜像

Ubuntu MATE 是仅有的原生支持树莓派且包含一个完整的桌面环境的发行版,选择安装Ubuntu Mate是最简单和快速的。

登录Ubuntu Mate官网[https://ubuntu-mate.org/download/](https://ubuntu-mate.org/download/)

选择Raspberry Pi 64位 镜像进入后，再选择20.04版本并下载镜像。

![](https://thundersoft.feishu.cn/space/api/box/stream/download/asynccode/?code=708dbe4b92a413c2c7d4c74943160bbf_8f118824ce50c961_boxcnIgVo45CNpljacQNcsLcUdh_FeFqItujS9gvKKyizoRRzB9ljMDgPyOk)

![](https://thundersoft.feishu.cn/space/api/box/stream/download/asynccode/?code=70b114d3a7dd68bb14e83ff61e4e8442_8f118824ce50c961_boxcnOjUc13kCz2SG0SvzWO8Zbc_h1Ztd4TkffMXevlOD1nZQYaOuJciFmKt)

镜像文件一旦下载完成后需要解压。请使用下面命令进行解压。

`$ xz -d ubuntu-mate***.img.xz （ubuntu-mate***.img.xz是你下载的镜像的名字）`

如下图所示即为镜像文件的.img.xz与.img形式。

![](https://thundersoft.feishu.cn/space/api/box/stream/download/asynccode/?code=ecd3a14d6c344432cc2139a1bf371939_8f118824ce50c961_boxcn5tN54Zdb4NRvwKYmwuvhof_aWZNJTHWZvnKQuQIpDq5C1ixgPHafPXN)

*   把镜像烧录至格式化好的SD卡中

需要用Balena Etcher工具烧写SD卡，制作树莓派的文件系统。

登录[https://www.balena.io/etcher/](https://www.balena.io/etcher/)后下载安装Linux版本Balena Etcher的安装包。

解压安装包并且安装Balena Etcher.

`unzip zipped_file.zip (zipped_file是安装包的名字)`

_|_![](https://thundersoft.feishu.cn/space/api/box/stream/download/asynccode/?code=a0ab53dc5e8ed794771c7bb1fc7ee652_8f118824ce50c961_boxcn3axZfBy4Ygjd0vXNzFggDg_8V43FIrO0mk5qxBxxwoInTyFDeEy6h8N)

启动 Etcher，选择镜像文件和 SD 卡，后点击Flash进行烧写。

_|_![](https://thundersoft.feishu.cn/space/api/box/stream/download/asynccode/?code=0efbc2f90a679dfd7a39ab315e435d9e_8f118824ce50c961_boxcnCh1qtCeydrIdNXTrjPDz2e_iMcyatFAeZzHcFOlzFAWI3MUFA3qkiug)

_|_![](https://thundersoft.feishu.cn/space/api/box/stream/download/asynccode/?code=d55cb6141f1550aafe9d06b118d815a0_8f118824ce50c961_boxcnWETyaQP25vpp3kudRnz5Tg_Cul8ap3div21tTftZR78k2sVjQozPtld)

当烧写成功后，balena Etcher界面如图所示

_|_![](https://thundersoft.feishu.cn/space/api/box/stream/download/asynccode/?code=55051e39016186f6fc531b8fe3fc65ad_8f118824ce50c961_boxcn0SqGD2oYnhZeVxz8sykFRg_LV6fgZeARU0q4E1U1A3zqZkPNbTwg9Lk)

启动树莓派

你需要一些外设才能使用树莓派，例如鼠标、键盘、HDMI 线等等。

插入一个鼠标和一个键盘。

连接 HDMI 线缆。

插入 SD 卡 到 SD 卡槽。

插入电源线给它供电。确保你有一个好的电源供应（5V、3A ）。一个不好的电源供应可能降低性能。

_|_![](https://thundersoft.feishu.cn/space/api/box/stream/download/asynccode/?code=c26d60d5f55718150f2fd45426883995_8f118824ce50c961_boxcnGWTVb0k9oytOZUpu6XGOOb_H5cBvVisCcx6zH2gP8OEDS1hPc8cFsSh)

_|_![](https://thundersoft.feishu.cn/space/api/box/stream/download/asynccode/?code=f22ea7826a4bf23eaf2cd8fa8ffdca12_8f118824ce50c961_boxcnnoRThEtvaPJyHAGoD8YIdc_tEC4az3fG75O21VcCG0D5ACcvIegW3Vg)

*   配置系统

当树莓派连接显示器后，启动树莓派系统进行配置，选择环境语言与所处地区。

_|_![](https://thundersoft.feishu.cn/space/api/file/out/zNKojAD83o8Ad7ijdZuUbWCbuvZGeMQFe8duU8KKc7DMwtTPCj/)

_|_![](https://thundersoft.feishu.cn/space/api/file/out/tU7Nnnh8mk82pGOik4XcFJr5nuED6osiDcNXk9mDiutdoDMbhc/)

选择你的 WiFi 网络并且在网络连接中输入密码。

_|_![](https://thundersoft.feishu.cn/space/api/file/out/uhTxboxP2jXgSz7TFEYRvPMQPDVmO48TagIiLEvrbRXBjNJ6GJ/)

在设置了键盘布局、时区和用户凭证后，在几分钟后你将被带到登录界面。

_|_![](https://thundersoft.feishu.cn/space/api/file/out/bd2K40TghayLi8Og1MEGamgD8tfWkKMnmxbvVGKo5mQc9evffM/)

*   连接设备

1.  显示器，键盘，鼠标
2.  ssh

将树莓派和pc电脑置于同一个局域网下，在树莓派终端上输入如下命令,看到只有ssh-agent 这个是ssh-client客户端服务，如果有sshd，证明你已经装好了ssh-server并已启用。

`$ ps -e|grep ssh`

如果没有，那就执行如下命令。输入yes回车继续安装。

`$ sudo apt install openssh-server`

_|_![](https://thundersoft.feishu.cn/space/api/box/stream/download/asynccode/?code=e64d2021b540863f7acee0ccdeedfeac_8f118824ce50c961_boxcnzxBXKIOQhC37utxGN1dSIe_UbNyneqgjxGD9NPojfdLEiCjkgnc8AtK)

安装完成后，再继续执行看到多了一个sshd，则可以用pc机远程登陆树莓派。

`$ ps -e|grep ssh`

_|_![](https://thundersoft.feishu.cn/space/api/box/stream/download/asynccode/?code=53d009108d4830f03ed7ac5c83a02e74_8f118824ce50c961_boxcn5Qvz2SSzqyo97RN9AH4WOc_gWfN3SQXr8g9ci4c8sZFPM1Nf9RdFZsP)

终端输入ifconfig后，在eth0端口查看树莓派的ip地址

`$ ifconfig`

_|_![](https://thundersoft.feishu.cn/space/api/box/stream/download/asynccode/?code=a76b09edace28972943b2c31e310820e_8f118824ce50c961_boxcns2fB8NY4Nwec3j4h7N96Re_8rYL24ROcDdsnQARg5ky7t2MUfNVdxIU)

在pc机中输入ssh '你的用户名'@'树莓派ip地址',并输入密码就可以通过ssh处理树莓派文件。

`$ ssh 'xxxxxxx'@'xx.xx.xx.xx'`

_|_![](https://thundersoft.feishu.cn/space/api/box/stream/download/asynccode/?code=2e18800d75f847d4eb508e0b5583207b_8f118824ce50c961_boxcn0n9Mok1YJTcMCeGYwvWeme_pe9rQ0h2OoYZS2lRq3HxSkXJGgRp80C1)

树莓派设备上安装EdgeX

*   安装方法简介

Edgex的安装有多种方式。

可以登录[https://github.com/edgexfoundry](https://github.com/edgexfoundry) 选择需要的project进行编译安装。

也可以按照[https://docs.edgexfoundry.org/1.2/getting-started/quick-start/](https://docs.edgexfoundry.org/1.2/getting-started/quick-start/)的cocker-compose quick start方法进行安装。

以下基于容器方法进行快速安装

*   安装Docker 

除去按如下指令安装docker外，也可登陆Docker官网[https://docs.docker.com/get-docker/](https://docs.docker.com/get-docker/)后选择Docker for Linux，再选择Ubuntu Platform 中的ARM64/AARCH64后根据提示进行Docker安装

_|_![](https://thundersoft.feishu.cn/space/api/box/stream/download/asynccode/?code=8ee9d7609c32e63c37e1c24d25c9a17e_8f118824ce50c961_boxcn9k7X2vghtmxjvdEVXrz19c_9u4GG3MBybr8Gc6jzm2sZwM9zeRQWSm1)

Uninstall old versions

Older versions of Docker were called docker, docker.io, or docker-engine. If these are installed, uninstall them:

`  $ sudo apt-get remove docker docker-engine docker.io containerd runc`

Install using the repository

Set up the repository

1\. Update the apt package index and install packages to allow apt to use a repository over HTTPS:

`$ sudo apt-get update`

`$ sudo apt-get install \`

`   apt-transport-https \`

`   ca-certificates \`

`   curl \`

`   gnupg-agent \`

`   software-properties-common`

2\. Add Docker’s official GPG key:

`$ curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -`

Verify that you now have the key with the fingerprint 9DC8 5822 9FC7 DD38 854A  E2D8 8D81 803C 0EBF CD88, by searching for the last 8 characters of the fingerprint.

`$ sudo apt-key fingerprint 0EBFCD88`

`pub   rsa4096 2017-02-22 [SCEA]`

`      9DC8 5822 9FC7 DD38 854A  E2D8 8D81 803C 0EBF CD88`

`uid           [ unknown] Docker Release (CE deb) <docker@docker.com>`

`sub   rsa4096 2017-02-22 [S]`

3\. Use the following command to set up the stable repository. To add the nightly or test repository, add the word nightly or test (or both) after the word stable in the commands below. 

`$ sudo add-apt-repository \`

`"deb [arch=arm64] https://download.docker.com/linux/ubuntu \`

`   $(lsb_release -cs) \`

`   stable"`

Install Docker Engine

1\. Update the apt package index, and install the latest version of Docker Engine and container, or go to the next step to install a specific version:

`$ sudo apt-get update`

`$ sudo apt-get install docker-ce docker-ce-cli containerd.io`

2\. Verify that Docker Engine is installed correctly by running the hello-world image.

`$ sudo docker run hello-world`

This command downloads a test image and runs it in a container. When the container runs, it prints an informational message and exits.

*   安装Docker-Compose

打开终端执行如下命令

`$ sudo apt install docker-compose`

如果下载的系统版本低于ubuntu20.0，上述操作可能会使docker-compose版本过旧，影响后续安装，所以需要用pip3来安装docker-compose

安装pip3

`$ sudo apt install python3-pip`

安装libffi

`$ sudo apt install libffi-dev`

安装docker-compose

`$ sudo pip3 install docker-compose`

当用pip3或者apt安装完成后测试docker-compose

`$ docker-compose -v`

显示

`Docker-compose version 1.xx.x, build unknown`

*   Running EdgeX

Once you have Docker and Docker Compose installed, you need to:

download / save the latest arm64 compose-files [https://github.com/edgexfoundry/developer-scripts/tree/master/releases/geneva/compose-files](https://github.com/edgexfoundry/developer-scripts/tree/master/releases/geneva/compose-files)

issue command to download and run the EdgeX Foundry Docker images from Docker Hub

This can be accomplished with a single command as shown below (please note the tabs for  ARM architectures).

`$ curl https://raw.githubusercontent.com/edgexfoundry/developer-scripts/master/releases/geneva/compose-files/docker-compose-geneva-redis-no-secty-arm64.yml -o docker-compose.yml;`

`$ docker-compose up -d`

_|_![](https://thundersoft.feishu.cn/space/api/box/stream/download/asynccode/?code=cd63a6ac2e2f8b72b266f6218a290c49_8f118824ce50c961_boxcnpUFZBB6XZEAv4QLmlaFngf_75ihyKvP9UqdM7daizhsww7R5o2nrRig)

验证EdgeX容器已经启动

`$sudo docker-compose ps`

_|_![](https://thundersoft.feishu.cn/space/api/box/stream/download/asynccode/?code=78e64f4feed5099bee60a6faf0a973b6_8f118824ce50c961_boxcnNNMiC4D8cmXj7VMgAk00ze_5jeYh6IjUc8Q1l0tT2zAWvqNQqKx2JSB)

Connecting a Device

EdgeX Foundry provides a [Random Number device service](https://github.com/edgexfoundry/device-random) which is useful to testing, it returns a random number within a configurable range. Configuration for running this service is in the docker-compose.yml file you downloaded at the start of this guide, but it is disabled by default. To enable it, uncomment the following lines in your docker-compose.yml:

`  device-random:`

`    image: edgexfoundry/docker-device-random-go:1.2.1`

`    ports:`

`- "127.0.0.1:49988:49988"`

`    container_name: edgex-device-random`

`    hostname: edgex-device-random`

`    networks:`

`      - edgex-network`

`    environment:`

`      <<: *common-variables`

`      Service_Host: edgex-device-random`

`    depends_on:`

`      - data`

`      - command`

Then you can start the Random device service with:

`docker-compose up -d device-random`

The device service will register a device named Random-Integer-Generator01, which will start sending its random number readings into EdgeX.

You can verify that those readings are being sent by querying the EdgeX core data service for the last 10 event records sent for Random-Integer-Generator01:

`curl http://localhost:48080/api/v1/event/device/Random-Integer-Generator01/10`

Controlling the Device and Exporting Data
