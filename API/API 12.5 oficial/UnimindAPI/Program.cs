using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

// Adiciona o contexto do banco de dados
builder.Services.AddDbContext<UsuarioDbContext>(options =>
options.UseSqlServer(builder.Configuration.GetConnectionString("DefaultConnection")));

builder.Services.AddDbContext<ProvaDbContext>(options =>
options.UseSqlServer(builder.Configuration.GetConnectionString("DefaultConnection")));

builder.Services.AddDbContext<QuestaoDbContext>(options =>
options.UseSqlServer(builder.Configuration.GetConnectionString("DefaultConnection")));

builder.Services.AddDbContext<ListaPersonalizadaDbContext>(options =>
options.UseSqlServer(builder.Configuration.GetConnectionString("DefaultConnection")));

builder.Services.AddDbContext<FlashcardDbContext>(options =>
options.UseSqlServer(builder.Configuration.GetConnectionString("DefaultConnection")));

builder.Services.AddDbContext<CompeticaoDbContext>(options =>
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

// USUARIOS

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


// PROVAS

// GET: /usuarios (Listar todos os usuarios)
app.MapGet("/provas", async (ProvaDbContext db) =>
    await db.Provas.ToListAsync());

// GET: /usuarios/{id} (Buscar um usuario por ID)
app.MapGet("/provas/{id}", async (int id, ProvaDbContext db) =>
    await db.Provas.FindAsync(id) is Prova prova ? Results.Ok(prova) : Results.NotFound());


// POST: /usuarios (Criar um novo usuario)
app.MapPost("/provas/add", async (Prova prova, ProvaDbContext db) =>
{
    db.Provas.Add(prova);    
    await db.SaveChangesAsync();
    return Results.Created($"/provas/{prova.IdProva}", prova);
});


// PUT: /usuarios/{id} (Atualizar um usuario existente)
app.MapPut("/provas/{id}", async (int id, Prova provaAtualizado, ProvaDbContext db) =>
{
    var prova = await db.Provas.FindAsync(id);
    if (prova is null) return Results.NotFound();

    if (provaAtualizado.IdFonte != null) 
        prova.IdFonte = provaAtualizado.IdFonte;

    if (provaAtualizado.Ano != null) 
        prova.Ano = provaAtualizado.Ano;

    if (provaAtualizado.QntdQuestoes != null) 
        prova.QntdQuestoes = provaAtualizado.QntdQuestoes;

    if (provaAtualizado.Fase != null) 
        prova.Fase = provaAtualizado.Fase;

    await db.SaveChangesAsync();
    return Results.NoContent();
});


// DELETE: /usuarios/{id} (Excluir um usuario)
app.MapDelete("/provas/del/{id}", async (int id, ProvaDbContext db) =>
{
    var prova = await db.Provas.FindAsync(id);
    if (prova is null) return Results.NotFound();
    db.Provas.Remove(prova);
    await db.SaveChangesAsync();
    return Results.NoContent();
});


// QUESTOES

// GET: /usuarios (Listar todos os usuarios)
app.MapGet("/questoes", async (QuestaoDbContext db) =>
    await db.Questoes.ToListAsync());

// GET: /usuarios/{id} (Buscar um usuario por ID)
app.MapGet("/questoes/{id}", async (int id, QuestaoDbContext db) =>
    await db.Questoes.FindAsync(id) is Questao questao ? Results.Ok(questao) : Results.NotFound());


// POST: /usuarios (Criar um novo usuario)
app.MapPost("/questoes/add", async (Questao questao, QuestaoDbContext db) =>
{
    db.Questoes.Add(questao);    
    await db.SaveChangesAsync();
    return Results.Created($"/questoes/{questao.IdQuestao}", questao);
});


// PUT: /usuarios/{id} (Atualizar um usuario existente)
app.MapPut("/questoes/{id}", async (int id, Questao questaoAtualizado, QuestaoDbContext db) =>
{
    var questao = await db.Questoes.FindAsync(id);
    if (questao is null) return Results.NotFound();

    if (questaoAtualizado.IdProva != null) 
        questao.IdProva = questaoAtualizado.IdProva;

    if (questaoAtualizado.IdCategoria != null) 
        questao.IdCategoria = questaoAtualizado.IdCategoria;

    if (questaoAtualizado.Pergunta != null) 
        questao.Pergunta = questaoAtualizado.Pergunta;

    if (questaoAtualizado.Resposta != null) 
        questao.Resposta = questaoAtualizado.Resposta;

    await db.SaveChangesAsync();
    return Results.NoContent();
});


// DELETE: /usuarios/{id} (Excluir um usuario)
app.MapDelete("/questoes/del/{id}", async (int id, QuestaoDbContext db) =>
{
    var questao = await db.Questoes.FindAsync(id);
    if (questao is null) return Results.NotFound();
    db.Questoes.Remove(questao);
    await db.SaveChangesAsync();
    return Results.NoContent();
});


// LISTAS PERSONALIZADAS

// GET: /usuarios (Listar todos os usuarios)
app.MapGet("/listas", async (ListaPersonalizadaDbContext db) =>
    await db.ListaPersonalizadas.ToListAsync());

// GET: /usuarios/{id} (Buscar um usuario por ID)
app.MapGet("/listas/{id}", async (int id, ListaPersonalizadaDbContext db) =>
    await db.ListaPersonalizadas.FindAsync(id) is ListaPersonalizada listaPersonalizada ? Results.Ok(listaPersonalizada) : Results.NotFound());


// POST: /usuarios (Criar um novo usuario)
app.MapPost("/listas/add", async (ListaPersonalizada listaPersonalizada, ListaPersonalizadaDbContext db) =>
{
    db.ListaPersonalizadas.Add(listaPersonalizada);    
    await db.SaveChangesAsync();
    return Results.Created($"/listas/{listaPersonalizada.IdLista}", listaPersonalizada);
});


// PUT: /usuarios/{id} (Atualizar um usuario existente)
app.MapPut("/listas/{id}", async (int id, ListaPersonalizada listaAtualizada, ListaPersonalizadaDbContext db) =>
{
    var lista = await db.ListaPersonalizadas.FindAsync(id);
    if (lista is null) return Results.NotFound();

    if (listaAtualizada.IdUsuario != null) 
        lista.IdUsuario = listaAtualizada.IdUsuario;

    if (listaAtualizada.IdCategoria != null) 
        lista.IdCategoria = listaAtualizada.IdCategoria;

    if (listaAtualizada.IdFonte != null) 
        lista.IdFonte = listaAtualizada.IdFonte;

    if (listaAtualizada.Ano != null) 
        lista.Ano = listaAtualizada.Ano;

    await db.SaveChangesAsync();
    return Results.NoContent();
});


// DELETE: /usuarios/{id} (Excluir um usuario)
app.MapDelete("/listas/del/{id}", async (int id, ListaPersonalizadaDbContext db) =>
{
    var lista = await db.ListaPersonalizadas.FindAsync(id);
    if (lista is null) return Results.NotFound();
    db.ListaPersonalizadas.Remove(lista);
    await db.SaveChangesAsync();
    return Results.NoContent();
});


// FLASHCARDS

// GET: /usuarios (Listar todos os usuarios)
app.MapGet("/flashcards", async (FlashcardDbContext db) =>
    await db.Flashcards.ToListAsync());

// GET: /usuarios/{id} (Buscar um usuario por ID)
app.MapGet("/flashcards/{id}", async (int id, FlashcardDbContext db) =>
    await db.Flashcards.FindAsync(id) is Flashcard flashcard ? Results.Ok(flashcard) : Results.NotFound());


// POST: /usuarios (Criar um novo usuario)
app.MapPost("/flashcards/add", async (Flashcard flashcard, FlashcardDbContext db) =>
{
    db.Flashcards.Add(flashcard);    
    await db.SaveChangesAsync();
    return Results.Created($"/flashcards/{flashcard.IdFlashcard}", flashcard);
});


// PUT: /usuarios/{id} (Atualizar um usuario existente)
app.MapPut("/flashcards/{id}", async (int id, Flashcard flashcardAtualizada, FlashcardDbContext db) =>
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
app.MapDelete("/flashcards/del/{id}", async (int id, FlashcardDbContext db) =>
{
    var flashcard = await db.Flashcards.FindAsync(id);
    if (flashcard is null) return Results.NotFound();
    db.Flashcards.Remove(flashcard);
    await db.SaveChangesAsync();
    return Results.NoContent();
});


// COMPETICAO

// GET: /usuarios (Listar todos os usuarios)
app.MapGet("/competicoes", async (CompeticaoDbContext db) =>
    await db.Competicoes.ToListAsync());

// GET: /usuarios/{id} (Buscar um usuario por ID)
app.MapGet("/competicoes/{id}", async (int id, CompeticaoDbContext db) =>
    await db.Competicoes.FindAsync(id) is Competicao competicao ? Results.Ok(competicao) : Results.NotFound());


// POST: /usuarios (Criar um novo usuario)
app.MapPost("/competicoes/add", async (Competicao competicao, CompeticaoDbContext db) =>
{
    db.Competicoes.Add(competicao);    
    await db.SaveChangesAsync();
    return Results.Created($"/competicoes/{competicao.IdCompeticao}", competicao);
});


// PUT: /usuarios/{id} (Atualizar um usuario existente)
app.MapPut("/competicoes/{id}", async (int id, Competicao competicaoAtualizada, CompeticaoDbContext db) =>
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
app.MapDelete("/competicoes/del/{id}", async (int id, CompeticaoDbContext db) =>
{
    var competicao = await db.Competicoes.FindAsync(id);
    if (competicao is null) return Results.NotFound();
    db.Competicoes.Remove(competicao);
    await db.SaveChangesAsync();
    return Results.NoContent();
});


app.Run();