using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace UnimindAPI.Migrations
{
    /// <inheritdoc />
    public partial class ListasComSentido : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "tempo",
                schema: "unimind",
                table: "ListaPersonalizada",
                type: "integer",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "titulo",
                schema: "unimind",
                table: "ListaPersonalizada",
                type: "text",
                nullable: false,
                defaultValue: "");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "tempo",
                schema: "unimind",
                table: "ListaPersonalizada");

            migrationBuilder.DropColumn(
                name: "titulo",
                schema: "unimind",
                table: "ListaPersonalizada");
        }
    }
}
