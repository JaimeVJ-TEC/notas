package com.tec.appnotas.ui.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.tec.appnotas.domain.dao.EventoDao
import com.tec.appnotas.ui.screens.calendario.CalendarioViewModel
import com.tec.appnotas.domain.models.Event
import java.text.DateFormatSymbols
import java.util.Calendar
import java.util.Locale

@Composable
fun MiCalendario(viewModel: CalendarioViewModel) {

    //EN CUALQUIER PARTE DONDE AGREGES O MODIFIQUES EVENTOS LLAMA EMTODOS DE CALENDARIO VIEWMODEL

    //CAMBIA A COLLECT STATE DEL VIEWMODEL
    val events = viewModel.events // Define una lista mutable para los eventos

    var currentMonth by remember { mutableStateOf(Calendar.getInstance().get(Calendar.MONTH)) }
    var currentYear by remember { mutableStateOf(Calendar.getInstance().get(Calendar.YEAR)) }
    var selectedDay by remember { mutableStateOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) }

    var eventTitle by remember { mutableStateOf("") }
    var eventBody by remember { mutableStateOf("") }
    var eventID by remember { mutableStateOf(0) }

    var showDialog by remember { mutableStateOf(false) } //Para mostrar el alert donde escribimos
    var showEvents by remember { mutableStateOf(false) } // Lista para mostrar los eventos

    val daysInMonth = getDaysInMonth(currentMonth, currentYear)
    val startDayOfMonth = getStartDayOfMonth(currentMonth, currentYear)

    if (currentMonth < 0) {
        currentMonth = 11
        currentYear--
    } else if (currentMonth > 11) {
        currentMonth = 0
        currentYear++
    }

    Column(modifier = Modifier.padding(16.dp))
    {

        Row {
            IconButton(onClick = { currentMonth-- }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Mes anterior")
            }
            Text(
                text = "${getMonthName(currentMonth)}, $currentYear",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
            )
            IconButton(onClick = { currentMonth++ }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Mes siguiente")
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            for (dayOfWeek in 1..7) {
                Text(
                    text = getDayOfWeekName(dayOfWeek),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        for (weekOfMonth in 1..6)
        {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (dayOfWeek in 1..7) {
                    val dayOfMonth = (7 * (weekOfMonth - 1)) + dayOfWeek - startDayOfMonth + 1
                    if (dayOfMonth < 1 || dayOfMonth > daysInMonth) {
                        Box(modifier = Modifier.weight(1f))
                    } else {
                        val dayColor = if (dayOfMonth == selectedDay && currentMonth == Calendar.getInstance().get(Calendar.MONTH) && currentYear == Calendar.getInstance().get(Calendar.YEAR)) Color.Cyan else Color.Unspecified
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clickable(onClick = {
                                    selectedDay = dayOfMonth
                                    showDialog = true
                                })
                                .background(dayColor)
                                .border(
                                    width = 1.dp,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(5.dp)
                                )
                        ) {
                            Text(
                                text = dayOfMonth.toString(),
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Agregar evento para el $selectedDay de ${getMonthName(currentMonth)} \n") },
                text = {
                    Column {
                        TextField(
                            value = eventTitle,
                            onValueChange = { eventTitle = it },
                            label = { Text("Título del evento") }
                        )
                        TextField(
                            value = eventBody,
                            onValueChange = { eventBody = it },
                            label = { Text("Descripción del evento") }
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            // Aquí podemos guardar el evento en algún lugar, como en una base de datos
                            if (eventTitle == "" || eventBody == ""){
                                showDialog = false
                            }else{
                                events.add(Event(title = "Titulo: $eventTitle", eventBody = "Descripcion: $eventBody", selectedDay = selectedDay, currentMonth = currentMonth))
                            }

                            eventTitle = ""
                            eventBody = ""
                            showDialog = false
                        }
                    ) {
                        Text("Agregar evento")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            eventTitle = ""
                            eventBody = ""
                            showDialog = false }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                modifier = Modifier.fillMaxWidth().padding(15.dp),
                onClick = {
                    showEvents = true },
            ) {
                Text("Mostrar los eventos del Mes")
            }
        }
    }

    if (showEvents) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 8.dp,
            backgroundColor = Color.White,
        ) {
            Column(modifier = Modifier.padding(16.dp))
            {
                Text(
                    "Eventos del mes de ${getMonthName(currentMonth)} $currentYear",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                if (events.isNotEmpty()) {
                    for (event in events) {
                        if (event.currentMonth == currentMonth) {
                            Text(
                                "Día ${event.selectedDay} \n${event.title}",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                event.eventBody,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }
                    }
                } else {
                    Text("No hay eventos este mes")
                }
                Button(
                    onClick = { showEvents = false },
                ) {
                    Text("Cerrar")
                }
            }
        }
    }
}



fun getDaysInMonth(month: Int, year: Int): Int {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month)
    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
}

fun getStartDayOfMonth(month: Int, year: Int): Int {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month)
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    return calendar.get(Calendar.DAY_OF_WEEK)
}

fun getMonthName(month: Int): String {
    return DateFormatSymbols().months[month]
}

fun getDayOfWeekName(dayOfWeek: Int): String {
    return DateFormatSymbols().shortWeekdays[dayOfWeek].uppercase(Locale.getDefault())
}



