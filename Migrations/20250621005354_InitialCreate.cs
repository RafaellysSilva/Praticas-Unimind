using System;
using Microsoft.EntityFrameworkCore.Migrations;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;

#nullable disable

namespace UnimindAPI.Migrations
{
    /// <inheritdoc />
    public partial class InitialCreate : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.EnsureSchema(
                name: "unimind");

            migrationBuilder.CreateTable(
                name: "Competicao",
                schema: "unimind",
                columns: table => new
                {
                    idCompeticao = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    data = table.Column<DateOnly>(type: "date", nullable: false),
                    idUsuario1 = table.Column<int>(type: "integer", nullable: false),
                    idUsuario2 = table.Column<int>(type: "integer", nullable: false),
                    idNivel = table.Column<int>(type: "integer", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Competicao", x => x.idCompeticao);
                });

            migrationBuilder.CreateTable(
                name: "Flashcard",
                schema: "unimind",
                columns: table => new
                {
                    idFlashcard = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    idUsuario = table.Column<int>(type: "integer", nullable: false),
                    perguntaUsuario = table.Column<string>(type: "text", nullable: false),
                    respostaUsuario = table.Column<string>(type: "text", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Flashcard", x => x.idFlashcard);
                });

            migrationBuilder.CreateTable(
                name: "ListaPersonalizada",
                schema: "unimind",
                columns: table => new
                {
                    idLista = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    idUsuario = table.Column<int>(type: "integer", nullable: false),
                    idCategoria = table.Column<int>(type: "integer", nullable: false),
                    idFonte = table.Column<int>(type: "integer", nullable: true),
                    ano = table.Column<int>(type: "integer", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ListaPersonalizada", x => x.idLista);
                });

            migrationBuilder.CreateTable(
                name: "Prova",
                schema: "unimind",
                columns: table => new
                {
                    idProva = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    idFonte = table.Column<int>(type: "integer", nullable: false),
                    ano = table.Column<int>(type: "integer", nullable: false),
                    qntdQuestoes = table.Column<int>(type: "integer", nullable: false),
                    fase = table.Column<string>(type: "text", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Prova", x => x.idProva);
                });

            migrationBuilder.CreateTable(
                name: "Questao",
                schema: "unimind",
                columns: table => new
                {
                    idQuestao = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    idProva = table.Column<int>(type: "integer", nullable: false),
                    idCategoria = table.Column<int>(type: "integer", nullable: false),
                    questao = table.Column<string>(type: "text", nullable: false),
                    resposta = table.Column<string>(type: "text", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Questao", x => x.idQuestao);
                });

            migrationBuilder.CreateTable(
                name: "Usuario",
                schema: "unimind",
                columns: table => new
                {
                    idUsuario = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    nome = table.Column<string>(type: "text", nullable: false),
                    email = table.Column<string>(type: "text", nullable: false),
                    senha = table.Column<string>(type: "text", nullable: false),
                    idNivel = table.Column<int>(type: "integer", nullable: true),
                    acertosQuestoes = table.Column<int>(type: "integer", nullable: true),
                    errosQuestoes = table.Column<int>(type: "integer", nullable: true),
                    tempoEstudo = table.Column<int>(type: "integer", nullable: true),
                    competicoesRealizadas = table.Column<int>(type: "integer", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Usuario", x => x.idUsuario);
                });
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Competicao",
                schema: "unimind");

            migrationBuilder.DropTable(
                name: "Flashcard",
                schema: "unimind");

            migrationBuilder.DropTable(
                name: "ListaPersonalizada",
                schema: "unimind");

            migrationBuilder.DropTable(
                name: "Prova",
                schema: "unimind");

            migrationBuilder.DropTable(
                name: "Questao",
                schema: "unimind");

            migrationBuilder.DropTable(
                name: "Usuario",
                schema: "unimind");
        }
    }
}
