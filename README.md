# nap_foreigners

firewall-cmd --zone=public --add-port=8080/tcp --permanent
firewall-cmd --reload

systemctl start spring-napforigners
systemctl status spring-napforigners
systemctl stop spring-napforigners