package co.proexe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.proexe.model.data.TvProgramme
import co.proexe.util.isSameDay
import co.proexe.vm.EpgViewModel
import coil.compose.rememberAsyncImagePainter
import java.text.DecimalFormat
import java.time.ZoneId
import java.util.*


@ExperimentalAnimationApi
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {

    private val viewModel: EpgViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.updateList()

        setContent {
            Parent(viewModel = viewModel)
        }

        super.onCreate(savedInstanceState)
    }


    @Composable
    fun Parent(viewModel: EpgViewModel) {
        Column(
            modifier = Modifier.background(colorResource(R.color.colorRecyclerViewChannelBackground)).fillMaxSize()
        ) {

            val tabTitles = viewModel.timeRepo.getDayTiles()
            var selectedTabIndex by remember { mutableStateOf(2) }

            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.height(50.dp).align(CenterHorizontally),
                backgroundColor = colorResource(R.color.colorDivider),
                contentColor = colorResource(R.color.colorText),
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        height = 0.dp,
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                    )
                }
            ) {
                tabTitles.forEachIndexed { index, tile ->
                    Tab(
                        selected = selectedTabIndex == index,
                        modifier = Modifier.height(50.dp).fillMaxWidth(),
                        onClick = { selectedTabIndex = index }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxHeight(),
                            contentAlignment = Center
                        ) {
                            Text(
                                text = stringResource(tile.dayLabel),
                                color = if (index == selectedTabIndex) colorResource(R.color.colorAccent) else colorResource(R.color.colorRecyclerViewTimeItemNotSelected),
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(R.color.colorRecyclerViewChannelBackground))

            ) {
                val date = Date(viewModel.timeRepo.getDayTiles()[selectedTabIndex].timestamp)
                val tvProgrammeState = viewModel.tvProgrammeFlow.collectAsState()
                TvProgrammeList(viewModel, tvProgrammeState, date)
            }
        }

    }

    @Composable
    fun TvProgrammeList(viewModel: EpgViewModel, tvProgrammeState: State<List<TvProgramme>>, date: Date) {

        val state by remember { tvProgrammeState }
        LazyColumn {
            items(items = state.filter { isSameDay(date, Date.from(it.startTimeDateRaw.atZone(ZoneId.systemDefault()).toInstant())) }.sortedDescending(), key = {
                it.id
            }, itemContent = { tvProgramme ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .background(colorResource(R.color.colorRecyclerViewChannelBackground))
                        .animateItemPlacement()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {
                                    val index = viewModel.tvProgrammesList.indexOfFirst { it.id == tvProgramme.id }
                                    viewModel.setFavourite(
                                        index,
                                        !viewModel.tvProgrammesList[index].isFavourite
                                    )
                                }
                            )
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp)
                            .padding(horizontal = 16.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(tvProgramme.imageUrl),
                            contentDescription = null,
                            modifier = Modifier.size(52.dp)
                        )

                        Column(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .weight(1f)
                        ) {
                            val f = DecimalFormat("00")
                            Text(text = tvProgramme.title, fontWeight = FontWeight.Bold, color = colorResource(R.color.colorText))
                            Text(text = "${tvProgramme.startTimeDateRaw.hour}:${f.format(tvProgramme.startTimeDateRaw.minute)}-${tvProgramme.endTimeDateRaw.hour}:${f.format(tvProgramme.endTimeDateRaw.minute)} | ${tvProgramme.type}", maxLines = 2, overflow = TextOverflow.Ellipsis, color = colorResource(R.color.colorText))
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = tvProgramme.progressPercent / 100f,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(3.dp)
                                    .clip(RoundedCornerShape(1.dp)),
                                color = colorResource(R.color.colorAccent),
                                backgroundColor = colorResource(R.color.colorDivider)
                            )

                        }

                        Icon(
                            painter = painterResource(R.drawable.ic_more_vert_white_24dp),
                            contentDescription = "More",
                            tint = colorResource(R.color.colorText),
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    Divider(
                        color = colorResource(R.color.colorDivider),
                        thickness = 1.dp
                    )
                }
            })
        }
    }

}