using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace TWTodos.Migrations
{
    /// <inheritdoc />
    public partial class InitialCreate : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Todos",
                columns: table => new
                {
                    IdUsuario = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Nome = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Email = table.Column<bool>(type: "bit", nullable: false),
                    Senha = table.Column<int>(type: "int", nullable: false),
                    IdNivel = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    AcertosQuestoes = table.Column<bool>(type: "bit", nullable: false),
                    ErrosQuestoes = table.Column<int>(type: "int", nullable: false),
                    TempoEstudo = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    CompeticoesRealizadas = table.Column<bool>(type: "bit", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Todos", x => x.IdUsuario);
                });
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Todos");
        }
    }
}
