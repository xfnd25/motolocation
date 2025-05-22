az group create --name rg-vmubuntu --location eastus

az network vnet create --resource-group rg-vmubuntu --name nnet-Linux --subnet-name subnet1

az network public-ip create --resource-group rg-vmubuntu --name pip-ubuntu

az network nsg create --resource-group rg-vmubuntu --name nsgsr-linux

az network nsg rule create --resource-group rg-vmubuntu --nsg-name nsgsr-linux --name Allow-SSH --protocol tcp --priority 1000 --destination-port-range 22 --access allow --direction inbound

az network nsg rule create --resource-group rg-vmubuntu --nsg-name nsgsr-linux --name Allow-App --protocol tcp --priority 1010 --destination-port-range 8080 --access allow --direction inbound

az vm create --resource-group rg-vmubuntu --name vm-ubuntu --image Canonical:UbuntuServer:19_04-gen2:19.04.201908230 --size Standard_B2s --vnet-name nnet-Linux --subnet subnet1 --nsg nsgsr-linux --public-ip-address pip-ubuntu --authentication-type password --admin-username admlnx --admin-password 'Fiap@2tdspZ@fFcp'

az vm show --resource-group rg-vmubuntu --name vm-ubuntu --show-details --query publicIps --output tsv
