using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

// Adiciona o contexto do banco de dados unificado
builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));

// Adiciona o suporte para endpoints da API e Swagger. Opcional.
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
var app = builder.Build();

using (var scope = app.Services.CreateScope())
{
    var db = scope.ServiceProvider.GetRequiredService<ApplicationDbContext>();
    db.Database.Migrate();
}


// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

// Mapeamento dos endpoints da API

// USUARIOS

// GET: /usuarios (Listar todos os usuarios)
app.MapGet("/usuarios", async (ApplicationDbContext db) =>
    await db.Usuarios.ToListAsync());

// GET: /usuarios/{id} (Buscar um usuario por ID)
app.MapGet("/usuarios/{id}", async (int id, ApplicationDbContext db) =>
    await db.Usuarios.FindAsync(id) is Usuario usuario ? Results.Ok(usuario) : Results.NotFound());

app.MapGet("/login/{user}/{password}", async (string user, string password, ApplicationDbContext db) =>
{
    var usuario = await db.Usuarios.FirstOrDefaultAsync(u => u.Nome == user && u.Senha == password);
    return usuario is not null ? Results.Ok(usuario) : Results.NotFound();
});

// POST: /usuarios (Criar um novo usuario)
app.MapPost("/usuarios/add", async (Usuario usuario, ApplicationDbContext db) =>
{
    db.Usuarios.Add(usuario);
    await db.SaveChangesAsync();
    return Results.Created($"/usuarios/{usuario.IdUsuario}", usuario);
});


// PUT: /usuarios/{id} (Atualizar um usuario existente)
app.MapPut("/usuarios/{id}", async (int id, Usuario usuarioAtualizado, ApplicationDbContext db) =>
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
app.MapDelete("/usuarios/del/{id}", async (int id, ApplicationDbContext db) =>
{
    var usuario = await db.Usuarios.FindAsync(id);
    if (usuario is null) return Results.NotFound();
    db.Usuarios.Remove(usuario);
    await db.SaveChangesAsync();
    return Results.NoContent();
});

// QUESTOES

// GET: /questoes (Listar todas as questoes)
app.MapGet("/questoes", async (ApplicationDbContext db) =>
    await db.Questoes.ToListAsync());

// GET: /questoes/{id} (Buscar uma questao por ID)
app.MapGet("/questoes/{id}", async (int id, ApplicationDbContext db) =>
    await db.Questoes.FindAsync(id) is Questao questao ? Results.Ok(questao) : Results.NotFound());

// GET: /questoes/ano/{ano} (Listar questoes por ano)
app.MapGet("/questoes/ano/{ano}", async (int ano, ApplicationDbContext db) =>
{
    var questoes = await db.Questoes.Where(q => q.Ano == ano).ToListAsync();
    return questoes.Any() ? Results.Ok(questoes) : Results.NotFound();
});

// GET: /questoes/categoria/{idCategoria} (Listar questoes por categoria)
app.MapGet("/questoes/categoria/{idCategoria}", async (int idCategoria, ApplicationDbContext db) =>
{
    var questoes = await db.Questoes.Where(q => q.IdCategoria == idCategoria).ToListAsync();
    return questoes.Any() ? Results.Ok(questoes) : Results.NotFound();
});

// GET: /questoes/fonte/{fonte} (Listar questoes por fonte)
app.MapGet("/questoes/fonte/{fonte}", async (string fonte, ApplicationDbContext db) =>
{
    var questoes = await db.Questoes.Where(q => q.Fonte == fonte).ToListAsync();
    return questoes.Any() ? Results.Ok(questoes) : Results.NotFound();
});

// POST: /questoes (Criar uma nova questao)
app.MapPost("/questoes/add", async (Questao questao, ApplicationDbContext db) =>
{
    db.Questoes.Add(questao);
    await db.SaveChangesAsync();
    return Results.Created($"/questoes/{questao.IdQuestao}", questao);
});

// PUT: /questoes/{id} (Atualizar uma questao existente)
app.MapPut("/questoes/{id}", async (int id, Questao questaoAtualizada, ApplicationDbContext db) =>

{
    var questao = await db.Questoes.FindAsync(id);
    if (questao is null) return Results.NotFound();

    if (questaoAtualizada.Ano != null)
        questao.Ano = questaoAtualizada.Ano;

    if (questaoAtualizada.IdCategoria != null)
        questao.IdCategoria = questaoAtualizada.IdCategoria;

    if (questaoAtualizada.Fonte != null)
        questao.Fonte = questaoAtualizada.Fonte;

    if (questaoAtualizada.Pergunta != null)
        questao.Pergunta = questaoAtualizada.Pergunta;

    if (questaoAtualizada.Resposta != null)
        questao.Resposta = questaoAtualizada.Resposta;

    await db.SaveChangesAsync();
    return Results.NoContent();
});
// DELETE: /questoes/{id} (Excluir uma questao)
app.MapDelete("/questoes/del/{id}", async (int id, ApplicationDbContext db) =>
{
    var questao = await db.Questoes.FindAsync(id);
    if (questao is null) return Results.NotFound();
    db.Questoes.Remove(questao);
    await db.SaveChangesAsync();
    return Results.NoContent();
});

// LISTAS PERSONALIZADAS

// GET: /usuarios (Listar todos os usuarios)
app.MapGet("/listas", async (ApplicationDbContext db) =>
    await db.ListaPersonalizadas.ToListAsync());

// GET: /usuarios/{id} (Buscar um usuario por ID)
app.MapGet("/listas/{id}", async (int id, ApplicationDbContext db) =>
    await db.ListaPersonalizadas.FindAsync(id) is ListaPersonalizada listaPersonalizada ? Results.Ok(listaPersonalizada) : Results.NotFound());


// POST: /usuarios (Criar um novo usuario)
app.MapPost("/listas/add", async (ListaPersonalizada listaPersonalizada, ApplicationDbContext db) =>
{
    db.ListaPersonalizadas.Add(listaPersonalizada);
    await db.SaveChangesAsync();
    return Results.Created($"/listas/{listaPersonalizada.IdLista}", listaPersonalizada);
});

// PUT: /listas/{id} (Atualizar uma lista personalizada existente)
app.MapPut("/listas/{id}", async (int id, ListaPersonalizada listaAtualizada, ApplicationDbContext db) =>
{
    var lista = await db.ListaPersonalizadas.FindAsync(id);
    if (lista is null) return Results.NotFound();

    if (listaAtualizada.IdUsuario != null)
        lista.IdUsuario = listaAtualizada.IdUsuario;

    if (listaAtualizada.IdCategoria != null)
        lista.IdCategoria = listaAtualizada.IdCategoria;

    if (listaAtualizada.Fonte != null)
        lista.Fonte = listaAtualizada.Fonte;

    if (listaAtualizada.Ano != null)
        lista.Ano = listaAtualizada.Ano;

    await db.SaveChangesAsync();
    return Results.NoContent();
});

// DELETE: /usuarios/{id} (Excluir um usuario)
app.MapDelete("/listas/del/{id}", async (int id, ApplicationDbContext db) =>
{
    var lista = await db.ListaPersonalizadas.FindAsync(id);
    if (lista is null) return Results.NotFound();
    db.ListaPersonalizadas.Remove(lista);
    await db.SaveChangesAsync();
    return Results.NoContent();
});


// FLASHCARDS

// GET: /usuarios (Listar todos os usuarios)
app.MapGet("/flashcards", async (ApplicationDbContext db) =>
    await db.Flashcards.ToListAsync());

// GET: /usuarios/{id} (Buscar um usuario por ID)
app.MapGet("/flashcards/{id}", async (int id, ApplicationDbContext db) =>
    await db.Flashcards.FindAsync(id) is Flashcard flashcard ? Results.Ok(flashcard) : Results.NotFound());


// POST: /usuarios (Criar um novo usuario)
app.MapPost("/flashcards/add", async (Flashcard flashcard, ApplicationDbContext db) =>
{
    db.Flashcards.Add(flashcard);
    await db.SaveChangesAsync();
    return Results.Created($"/flashcards/{flashcard.IdFlashcard}", flashcard);
});


// PUT: /usuarios/{id} (Atualizar um usuario existente)
app.MapPut("/flashcards/{id}", async (int id, Flashcard flashcardAtualizada, ApplicationDbContext db) =>
{
    var flashcard = await db.Flashcards.FindAsync(id);
    if (flashcard is null) return Results.NotFound();

    if (flashcardAtualizada.IdUsuario != null)
        flashcard.IdUsuario = flashcardAtualizada.IdUsuario;

    if (flashcardAtualizada.PerguntaUsuario != null)
        flashcard.PerguntaUsuario = flashcardAtualizada.PerguntaUsuario;

    if (flashcardAtualizada.RespostaUsuario != null)
        flashcard.RespostaUsuario = flashcardAtualizada.RespostaUsuario;

    await db.SaveChangesAsync();
    return Results.NoContent();
});


// DELETE: /usuarios/{id} (Excluir um usuario)
app.MapDelete("/flashcards/del/{id}", async (int id, ApplicationDbContext db) =>
{
    var flashcard = await db.Flashcards.FindAsync(id);
    if (flashcard is null) return Results.NotFound();
    db.Flashcards.Remove(flashcard);
    await db.SaveChangesAsync();
    return Results.NoContent();
});


// COMPETICAO

// GET: /usuarios (Listar todos os usuarios)
app.MapGet("/competicoes", async (ApplicationDbContext db) =>
    await db.Competicoes.ToListAsync());

// GET: /usuarios/{id} (Buscar um usuario por ID)
app.MapGet("/competicoes/{id}", async (int id, ApplicationDbContext db) =>
    await db.Competicoes.FindAsync(id) is Competicao competicao ? Results.Ok(competicao) : Results.NotFound());


// POST: /usuarios (Criar um novo usuario)
app.MapPost("/competicoes/add", async (Competicao competicao, ApplicationDbContext db) =>
{
    db.Competicoes.Add(competicao);
    await db.SaveChangesAsync();
    return Results.Created($"/competicoes/{competicao.IdCompeticao}", competicao);
});


// PUT: /usuarios/{id} (Atualizar um usuario existente)
app.MapPut("/competicoes/{id}", async (int id, Competicao competicaoAtualizada, ApplicationDbContext db) =>
{
    var competicao = await db.Competicoes.FindAsync(id);
    if (competicao is null) return Results.NotFound();

    if (competicaoAtualizada.Data != null)
        competicao.Data = competicaoAtualizada.Data;

    if (competicaoAtualizada.IdUsuario1 != null)
        competicao.IdUsuario1 = competicaoAtualizada.IdUsuario1;

    if (competicaoAtualizada.IdUsuario2 != null)
        competicao.IdUsuario2 = competicaoAtualizada.IdUsuario2;

    if (competicaoAtualizada.IdNivel != null)
        competicao.IdNivel = competicaoAtualizada.IdNivel;

    await db.SaveChangesAsync();
    return Results.NoContent();
});


// DELETE: /usuarios/{id} (Excluir um usuario)
app.MapDelete("/competicoes/del/{id}", async (int id, ApplicationDbContext db) =>
{
    var competicao = await db.Competicoes.FindAsync(id);
    if (competicao is null) return Results.NotFound();
    db.Competicoes.Remove(competicao);
    await db.SaveChangesAsync();
    return Results.NoContent();
});

app.Run("http://0.0.0.0:5133");