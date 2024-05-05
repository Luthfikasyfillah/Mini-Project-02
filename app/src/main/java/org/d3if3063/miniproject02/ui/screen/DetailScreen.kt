package org.d3if3063.miniproject02.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3063.miniproject02.R
import org.d3if3063.miniproject02.database.PasienDb
import org.d3if3063.miniproject02.ui.theme.MiniProject02Theme
import org.d3if3063.miniproject02.util.ViewModelFactory


const val KEY_ID_PASIEN = "idPasien"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null){
    val contenx = LocalContext.current
    val db = PasienDb.getInstance(contenx)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var namaHewan by remember { mutableStateOf("") }
    var jenisHewan by remember { mutableStateOf("") }
    var jenisKelaminHewan by remember { mutableStateOf("") }
    var umurHewan by remember { mutableStateOf("") }
    var namaPemilik by remember { mutableStateOf("") }
    var noTelp by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getPasien(id) ?: return@LaunchedEffect
        namaHewan = data.namaHewan
        jenisHewan = data.jenisHewan
        jenisKelaminHewan = data.jenisKelaminHewan
        umurHewan = data.umurHewan
        namaPemilik = data.namaPemilik
        noTelp = data.noTelp
        alamat = data.alamat
    }

    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_pasien))
                    else
                        Text(text = stringResource(id = R.string.edit_pasien))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        if (namaHewan == "" || jenisHewan == "" || jenisKelaminHewan == "" || umurHewan == "" || namaPemilik == "" || noTelp == "" || alamat == "") {
                            Toast.makeText(contenx, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null) {
                            viewModel.insert(namaHewan, jenisHewan, jenisKelaminHewan, umurHewan, namaPemilik, noTelp, alamat)
                        } else {
                            viewModel.update(id, namaHewan, jenisHewan, jenisKelaminHewan, umurHewan, namaPemilik, noTelp, alamat)
                        }
                        navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if ( id != null ) {
                        DeleteAction { showDialog = true}
                        DisplayAlertDialog(
                            openDialog = showDialog,
                            onDismissRequest = { showDialog = false }) {
                            showDialog = false
                            viewModel.delete(id)
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    ) { padding ->
        FormPasien(
            namaHewan = namaHewan,
            onNamaHewanChange = { namaHewan = it },
            jenisHewan = jenisHewan,
            onJenisChange = { jenisHewan = it },
            jenisKelaminHewan = jenisKelaminHewan,
            onJenisKelaminChange = { jenisKelaminHewan = it },
            umurHewan = umurHewan,
            onUmurChange = { umurHewan = it },
            namaPemilik = namaPemilik,
            onNamaPemilikChange = { namaPemilik = it },
            noTelp = noTelp,
            onNoTelpChange = { noTelp = it },
            alamat = alamat,
            onAlamatChange = { alamat = it },
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var  expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.hapus))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}

@Composable
fun FormPasien(
    namaHewan: String, onNamaHewanChange: (String) -> Unit,
    jenisHewan: String, onJenisChange: (String) -> Unit,
    jenisKelaminHewan: String, onJenisKelaminChange: (String) -> Unit,
    umurHewan: String, onUmurChange: (String) -> Unit,
    namaPemilik: String, onNamaPemilikChange: (String) -> Unit,
    noTelp: String, onNoTelpChange: (String) -> Unit,
    alamat: String, onAlamatChange: (String) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = namaHewan,
            onValueChange = { onNamaHewanChange(it) },
            label = { Text(text = stringResource(R.string.namaHewan)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = jenisHewan,
            onValueChange = { onJenisChange(it) },
            label = { Text(text = stringResource(R.string.jenisHewan)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp)
        ) {
            Row {
                listOf(
                    "Jantan",
                    "Betina"
                ).forEach { classOption ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable { onJenisKelaminChange(classOption) }
                            .padding(horizontal = 16.dp)
                    ) {
                        RadioButton(
                            selected = classOption == jenisKelaminHewan,
                            onClick = { onJenisKelaminChange(classOption) }
                        )
                        Text(text = classOption, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        }
        OutlinedTextField(
            value = umurHewan,
            onValueChange = { onUmurChange(it) },
            label = { Text(text = stringResource(R.string.umurHewan)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = namaPemilik,
            onValueChange = { onNamaPemilikChange(it) },
            label = { Text(text = stringResource(R.string.namaPemilik)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = noTelp,
            onValueChange = { onNoTelpChange(it) },
            label = { Text(text = stringResource(R.string.noTelp)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = alamat,
            onValueChange = { onAlamatChange(it) },
            label = { Text(text = stringResource(R.string.alamat)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    MiniProject02Theme {
        DetailScreen(rememberNavController())
    }
}