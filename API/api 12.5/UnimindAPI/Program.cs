using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

// Adiciona o contexto do banco de dados
builder.Services.AddDbContext<UsuarioDbContext>(options =>
options.UseSqlServer(builder.Configuration.GetConnectionString("DefaultConnection")));

// Adiciona o suporte para endpoints da API e Swagger. Opcional.
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
var app = builder.Build();

// Configure the HTTP request pipeline. Opcional.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}
app.UseHttpsRedirection();

// Mapeamento dos endpoints da API

// GET: /usuarios (Listar todos os usuarios)
app.MapGet("/usuarios", async (UsuarioDbContext db) =>
    await db.Usuarios.ToListAsync());

// GET: /usuarios/{id} (Buscar um usuario por ID)
app.MapGet("/usuarios/{id}", async (int id, UsuarioDbContext db) =>
    await db.Usuarios.FindAsync(id) is Usuario usuario ? Results.Ok(usuario) : Results.NotFound());


// POST: /usuarios (Criar um novo usuario)
app.MapPost("/usuarios/add", async (Usuario usuario, UsuarioDbContext db) =>
{
    db.Usuarios.Add(usuario);    
    await db.SaveChangesAsync();
    return Results.Created($"/usuarios/{usuario.IdUsuario}", usuario);
});


// PUT: /usuarios/{id} (Atualizar um usuario existente)
app.MapPut("/usuarios/{id}", async (int id, Usuario usuarioAtualizado, UsuarioDbContext db) =>
{
    var usuario = await db.Usuarios.FindAsync(id);
    if (usuario is null) return Results.NotFound();

     if (usuarioAtualizado.Nome != null) 
        usuario.Nome = usuarioAtualizado.Nome;

    if (usuarioAtualizado.Email != null) 
        usuario.Email = usuarioAtualizado.Email;

    if (usuarioAtualizado.Senha != null) 
        usuario.Senha = usuarioAtualizado.Senha;

    if (usuarioAtualizado.IdNivel != null) 
        usuario.IdNivel = usuarioAtualizado.IdNivel;

    if (usuarioAtualizado.AcertosQuestoes != null) 
        usuario.AcertosQuestoes = usuarioAtualizado.AcertosQuestoes;

    if (usuarioAtualizado.ErrosQuestoes != null) 
        usuario.ErrosQuestoes = usuarioAtualizado.ErrosQuestoes;

    if (usuarioAtualizado.TempoEstudo != null) 
        usuario.TempoEstudo = usuarioAtualizado.TempoEstudo;

    if (usuarioAtualizado.CompeticoesRealizadas != null) 
        usuario.CompeticoesRealizadas = usuarioAtualizado.CompeticoesRealizadas;
    
    await db.SaveChangesAsync();
    return Results.NoContent();
});


// DELETE: /usuarios/{id} (Excluir um usuario)
app.MapDelete("/usuarios/del/{id}", async (int id, UsuarioDbContext db) =>
{
    var usuario = await db.Usuarios.FindAsync(id);
    if (usuario is null) return Results.NotFound();
    db.Usuarios.Remove(usuario);
    await db.SaveChangesAsync();
    return Results.NoContent();
});

app.Run();