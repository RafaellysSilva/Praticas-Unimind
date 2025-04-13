//var builder = WebApplication.CreateBuilder(args);
// var app = builder.Build();

// app.MapGet("/", () => "Hello World!");

// app.Run();

//api

//carregar variaveis env p dotenv
//porta
//conexao jwt
//interagir com ssms
//conecta bd
//faz uma query
//cria uma rota

using Microsoft.Data.SqlClient;
using System.Text;
using Microsoft.IdentityModel.Tokens;
using DotNetEnv;

// carrega as variaveis do ambiente .env
Env.Load();

//esse daqui ja vem no inicio
var builder = WebApplication.CreateBuilder(args);

//acessas as variaveis
string porta = Env.GetString("PORTA", "5000"); 
string connectionString = Env.GetString("CONNECTION_STRING");
string jwtSecret = Env.GetString("JWT_SECRET");

// JWT Auth config
var keyBytes = Encoding.UTF8.GetBytes(jwtSecret);

builder.Services.AddAuthentication("Bearer").AddJwtBearer("Bearer", options => {
    options.TokenValidationParameters = new TokenValidationParameters {
        ValidateIssuer = false,
        ValidateAudience = false,
        ValidateIssuerSigningKey = true,
        IssuerSigningKey = new SymmetricSecurityKey(keyBytes)
    };
});

builder.Services.AddCors();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddAuthorization();

//esse daqui ja vem no inicio
var app = builder.Build();

app.UseCors(x => x.AllowAnyOrigin().AllowAnyHeader().AllowAnyMethod());
app.UseAuthentication();
app.UseAuthorization();


// execquery: função para executar a consulta SQL de forma assíncrona
static async Task<List<Dictionary<string, object>>> execQuery(string query) {
    var result = new List<Dictionary<string, object>>();

    try {
        // Crie uma conexão com o banco de dados
        using (var connection = new SqlConnection(Env.GetString("CONNECTION_STRING"))) {
            await connection.OpenAsync();  // abra a conexão assíncrona

            using (var command = new SqlCommand(query, connection)) {
                using (var reader = await command.ExecuteReaderAsync()) {
                    // enquanto houver registros
                    while (await reader.ReadAsync()) {
                        var row = new Dictionary<string, object>();

                        // adiciona cada coluna na linha de resultados
                        for (int i = 0; i < reader.FieldCount; i++) {
                            row[reader.GetName(i)] = reader.GetValue(i);
                        }

                        result.Add(row);  // adiciona a linha na lista de resultados
                    }
                }
            }
        }
    }
    catch (Exception ex) {
        Console.WriteLine($"Erro ao executar consulta: {ex.Message}");
    }

    return result;
}

// rota para obter todos os usuários
app.MapGet("/usuarios", async (HttpContext context) =>
{
    try {
        var query = "SELECT * FROM unimind.Usuario";  // consulta SQL
        var results = await execQuery(query);        // executa a consulta 

        // retorna os resultados como JSON, se tiver resultados
        if (results.Any()) {
            return Results.Json(results);
        }
        else {
            return Results.Json(new { message = "Nenhum usuário encontrado" }, statusCode: 404);
        }
    }
    catch (Exception ex) {
        return Results.Problem(detail: ex.Message, statusCode: 500, title: "Erro ao obter os usuários");
    }
});

// rota geral/principal
app.MapGet("/", () => Results.Json(new { message = "Servidor rodando" }));

// rodar
app.Run($"http://localhost:{porta}");