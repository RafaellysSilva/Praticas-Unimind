# Estágio de Build: Compila a aplicação
# Use a imagem do SDK do .NET correspondente à versão do seu projeto (ex: 8.0, 7.0, 6.0)
FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
WORKDIR /src

# Copia os arquivos .csproj e restaura as dependências
COPY *.csproj .
RUN dotnet restore

# Copia o restante do código-fonte e faz o build
COPY . .
RUN dotnet build "UnimindAPI.csproj" -c Release -o /app/build

# Estágio de Publicação: Cria a versão final otimizada da aplicação
FROM build AS publish
RUN dotnet publish "UnimindAPI.csproj" -c Release -o /app/publish /p:UseAppHost=false

# Estágio Final: Executa a aplicação
# Usa uma imagem base menor e mais segura, apenas com o runtime do .NET
FROM mcr.microsoft.com/dotnet/aspnet:8.0
WORKDIR /app
COPY --from=publish /app/publish .

# O Render define a porta através da variável de ambiente PORT.
# O entrypoint garante que sua API ouvirá na porta correta.
ENTRYPOINT ["dotnet", "UnimindAPI.dll"]