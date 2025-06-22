using Microsoft.EntityFrameworkCore.Migrations;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;

#nullable disable

namespace UnimindAPI.Migrations
{
    /// <inheritdoc />
    public partial class QuestoesComAlternativa : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "resposta",
                schema: "unimind",
                table: "Questao");

            migrationBuilder.CreateTable(
                name: "Alternativa",
                schema: "unimind",
                columns: table => new
                {
                    idAlternativa = table.Column<int>(type: "integer", nullable: false)
                        .Annotation("Npgsql:ValueGenerationStrategy", NpgsqlValueGenerationStrategy.IdentityByDefaultColumn),
                    texto = table.Column<string>(type: "text", nullable: false),
                    correta = table.Column<bool>(type: "boolean", nullable: false),
                    idQuestao = table.Column<int>(type: "integer", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Alternativa", x => x.idAlternativa);
                    table.ForeignKey(
                        name: "FK_Alternativa_Questao_idQuestao",
                        column: x => x.idQuestao,
                        principalSchema: "unimind",
                        principalTable: "Questao",
                        principalColumn: "idQuestao",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Alternativa_idQuestao",
                schema: "unimind",
                table: "Alternativa",
                column: "idQuestao");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Alternativa",
                schema: "unimind");

            migrationBuilder.AddColumn<string>(
                name: "resposta",
                schema: "unimind",
                table: "Questao",
                type: "text",
                nullable: false,
                defaultValue: "");
        }
    }
}
