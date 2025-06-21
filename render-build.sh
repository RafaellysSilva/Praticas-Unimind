#!/usr/bin/env bash
# exit on error
set -o errexit

# Instala o dotnet-ef se ainda não estiver instalado
dotnet tool install --global dotnet-ef

# Adiciona a ferramenta ao PATH
export PATH="$PATH:$HOME/.dotnet/tools"

dotnet build --configuration Release

# Aplica as migrações do banco de dados
# O --context é crucial aqui!
dotnet ef database update --context ApplicationDbContext