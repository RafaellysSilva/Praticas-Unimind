using Microsoft.EntityFrameworkCore.Migrations;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;

#nullable disable

namespace UnimindAPI.Migrations
{
    /// <inheritdoc />
    public partial class AlteraFonteParaString : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Prova",
                schema: "unimind");

            migrationBuilder.DropColumn(
                name: "idFonte",
                schema: "unimind",
                table: "ListaPersonalizada");

            migrationBuilder.RenameColumn(
                name: "idProva",
                schema: "unimind",
                table: "Questao",
                newName: "ano");

            migrationBuilder.AddColumn<string>(
                name: "fonte",
                schema: "unimind",
                table: "Questao",
                type: "text",
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<string>(
                name: "fonte",
                schema: "unimind",
                table: "ListaPersonalizada",
                type: "text",
                nullable: true);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "fonte",
                schema: "unimind",
                table: "Questao");

            migrationBuilder.DropColumn(
                name: "fonte",
                schema: "unimind",
                table: "ListaPersonalizada");

            migrationBuilder.RenameColumn(
                name: "ano",
                schema: "unimind",
                table: "Questao",
                newName: "idProva");

            migrationBuilder.AddColumn<int>(
                name: "idFonte",
                schema: "unimind",
                table: "ListaPersonalizada",
                type: "integer",
                nullable: true);

            migrationBuilder.CreateTable(
                name: "Prova",
                schema: "unimind",
                columns: table => new
                {
                    idProva = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    ano = table.Column<int>(type: "integer", nullable: false),
                    fase = table.Column<string>(type: "text", nullable: true),
                    idFonte = table.Column<int>(type: "integer", nullable: false),
                    qntdQuestoes = table.Column<int>(type: "integer", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Prova", x => x.idProva);
                });
        }
    }
}
