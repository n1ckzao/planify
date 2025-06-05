package com.example.planifyeventos.exposed

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Categoria(
    val id: Int,
    val nome: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriaDropdown(
    categorias: List<Categoria>,
    onCategoriaSelecionada: (Int?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }
    var selectedId by remember { mutableStateOf<Int?>(null) }

    // Box para conter o TextField e o Dropdown
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            // TextField que mostra a seleção atual e abre/fecha o menu
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                value = selectedText,
                onValueChange = {},
                label = { Text("Selecione uma categoria") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
            )

            // Menu dropdown com as opções
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categorias.forEach { categoria ->
                    DropdownMenuItem(
                        text = { Text(categoria.nome) },
                        onClick = {
                            selectedText = categoria.nome
                            selectedId = categoria.id
                            expanded = false
                            onCategoriaSelecionada(categoria.id)
                        }
                    )
                }
            }
        }
    }
}

// Exemplo de uso
@Composable
fun TelaExemplo() {
    val categorias = remember {
        listOf(
            Categoria(id = 1, nome = "Eletrônicos"),
            Categoria(id = 2, nome = "Roupas"),
            Categoria(id = 3, nome = "Alimentos")
        )
    }

    var categoriaSelecionadaId by remember { mutableStateOf<Int?>(null) }

    Column {
        CategoriaDropdown(
            categorias = categorias,
            onCategoriaSelecionada = { id ->
                categoriaSelecionadaId = id
                // Aqui você tem o ID da categoria selecionada
                println("Categoria selecionada ID: $id")
            }
        )

        // Exemplo de como usar o ID selecionado
        categoriaSelecionadaId?.let { id ->
            Text(
                text = "ID selecionado: $id",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}