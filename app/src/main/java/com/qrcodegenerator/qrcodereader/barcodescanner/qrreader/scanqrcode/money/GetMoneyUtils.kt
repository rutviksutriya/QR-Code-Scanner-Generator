package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.money


import android.app.Activity
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.R
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.gameURL
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.predChampURL
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.Constant.qurekaURL
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.common.isCheckNotEmpty
import com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.ExtraAdsModel
import java.util.Random

object GetMoneyUtils {
    fun descriptionList(getMoneyActivity: Activity): ExtraAdsModel? {
        val extraAdsModels = ArrayList<ExtraAdsModel>()
        if (predChampURL.isCheckNotEmpty()) {
            extraAdsModels.add(
                ExtraAdsModel(getMoneyActivity.getString(R.string.preadchamp_play_game), "Play & Win Coins Daily", "Come play Cricket Match Prediction for 50,000 Coins daily.", R.drawable.ic_pre_money_inters_banner1, R.drawable.ic_pre_money_gif_1, predChampURL)
            )
            extraAdsModels.add(ExtraAdsModel(getMoneyActivity.getString(R.string.preadchamp_play_game), "Sports Prediction Contest Live!", "Predict & Win | No install required", R.drawable.ic_pre_money_inters_banner2, R.drawable.ic_pre_money_gif_2, predChampURL))
            extraAdsModels.add(ExtraAdsModel(getMoneyActivity.getString(R.string.preadchamp_play_game), "Today Match Toss Win Prediction Contest", "Predict and Win Now!", R.drawable.ic_pre_money_inters_banner3, R.drawable.ic_pre_money_gif_3, predChampURL))
            extraAdsModels.add(ExtraAdsModel(getMoneyActivity.getString(R.string.preadchamp_play_game), "Pool prize is 50,000 coins", "Cricket  Prediction Contest is Live! Predict Now", R.drawable.ic_pre_money_inters_banner4, R.drawable.ic_pre_money_gif_4, predChampURL))
            extraAdsModels.add(ExtraAdsModel(getMoneyActivity.getString(R.string.preadchamp_play_game), "Prediction Time to Win Coins!", "Contests Live. Play Now", R.drawable.ic_pre_money_inters_banner5, R.drawable.ic_pre_money_gif_5, predChampURL))
            extraAdsModels.add(
                ExtraAdsModel(
                    getMoneyActivity.getString(R.string.preadchamp_play_game),
                    "Predict the match score & Win Coins now!",
                    "Predict & Win",
                    R.drawable.ic_pre_money_inters_banner6,
                    R.drawable.ic_pre_money_gif_6,
                    predChampURL
                )
            )
            extraAdsModels.add(
                ExtraAdsModel( //97
                    getMoneyActivity.getString(R.string.preadchamp_play_game),
                    "Who will win Cricket match today?",
                    "Predict Now & Win Coins",
                    R.drawable.ic_pre_money_inters_banner7,
                    R.drawable.ic_pre_money_gif_7,
                    predChampURL
                )
            )
            extraAdsModels.add(
                ExtraAdsModel( //98
                    getMoneyActivity.getString(R.string.preadchamp_play_game),
                    "Time to Win Now!",
                    "Play Bollywood Prediction  & win coins daily",
                    R.drawable.ic_pre_money_small_native1,
                    R.drawable.ic_pre_money_gif_1,
                    predChampURL
                )
            )
            extraAdsModels.add(
                ExtraAdsModel( //99
                    getMoneyActivity.getString(R.string.preadchamp_play_game),
                    "Innings for 50,000 coins",
                    "Prediction Champ Khelo aur Jeeto coins. No install required.",
                    R.drawable.ic_pre_money_small_native2,
                    R.drawable.ic_pre_money_gif_2,
                    predChampURL
                )
            )
            extraAdsModels.add(
                ExtraAdsModel( //100
                    getMoneyActivity.getString(R.string.preadchamp_play_game),
                    "Best Match Prediction game Today",
                    "Predict results & Win Now!",
                    R.drawable.ic_pre_money_small_native3,
                    R.drawable.ic_pre_money_gif_3,
                    predChampURL
                )
            )
        }
        if (qurekaURL.isCheckNotEmpty()) {
            extraAdsModels.add(
                ExtraAdsModel( //1
                    getMoneyActivity.getString(R.string.bitcoin_play_game),
                    "Aaj Math Quiz khela kya?",
                    "Play and win coins now",
                    R.drawable.ic_extra_inters_banner1,  //Bansi
                    R.drawable.ic_extra_gif_1,
                    qurekaURL
                )
            )
            extraAdsModels.add(
                ExtraAdsModel( //2
                    getMoneyActivity.getString(R.string.bitcoin_play_game),
                    "Time to Win Now!",
                    "Play Bollywood Quizzes  & win coins daily",
                    R.drawable.ic_extra_inters_banner2,
                    R.drawable.ic_extra_gif_2,
                    qurekaURL
                )
            )
            extraAdsModels.add(
                ExtraAdsModel( //3
                    getMoneyActivity.getString(R.string.bitcoin_play_game),
                    "Jeeto 10,000 Coins",
                    "Show your Math knowledge & Win Now",
                    R.drawable.ic_extra_inters_banner3,
                    R.drawable.ic_extra_gif_3,
                    qurekaURL
                )
            )
            extraAdsModels.add(
                ExtraAdsModel( //4
                    getMoneyActivity.getString(R.string.bitcoin_play_game),
                    "Tech quiz for 50,000 coins open",
                    "Test your tech skills & win now",
                    R.drawable.ic_extra_inters_banner4,
                    R.drawable.ic_extra_gif_4,
                    qurekaURL
                )
            )
            extraAdsModels.add(
                ExtraAdsModel( //5
                    getMoneyActivity.getString(R.string.bitcoin_play_game),
                    "Pool prize is 50,000 coins",
                    "Sharpen your Cricket knowledge & win now",
                    R.drawable.ic_extra_inters_banner5,
                    R.drawable.ic_extra_gif_5,
                    qurekaURL
                )
            )
        }
        if (gameURL.isCheckNotEmpty()) {
            extraAdsModels.add(
                ExtraAdsModel( //game1
                    getMoneyActivity.getString(R.string.play_games),
                    "Play Candy Burst To Win",
                    "Your chance of winning is high here! Play Now",
                    R.drawable.ic_money_small_native1,
                    R.drawable.ic_money_gif_1,
                    gameURL
                )
            )
            extraAdsModels.add(
                ExtraAdsModel( //game2
                    getMoneyActivity.getString(R.string.play_games),
                    "Play Bubble Shooter To Win",
                    "Pool prize is 1,00,000 coins",
                    R.drawable.ic_money_small_native2,
                    R.drawable.ic_money_gif_2,
                    gameURL
                )
            )
            extraAdsModels.add(
                ExtraAdsModel( //game3
                    getMoneyActivity.getString(R.string.play_games),
                    "Play MGL Games",
                    "Earn Upto 1,00,000 Coins Daily",
                    R.drawable.ic_money_small_native3,
                    R.drawable.ic_money_gif_3,
                    gameURL
                )
            )
            extraAdsModels.add(
                ExtraAdsModel( //game4
                    getMoneyActivity.getString(R.string.play_games),
                    "Play Fruit Chop Game",
                    "No install required! Play Game Now",
                    R.drawable.ic_money_small_native4,
                    R.drawable.ic_money_gif_4,
                    gameURL
                )
            )
            extraAdsModels.add(
                ExtraAdsModel( //game5
                    getMoneyActivity.getString(R.string.play_games),
                    "Play Games To Win Coins",
                    "Your chance of winning is high here! Play Now",
                    R.drawable.ic_money_small_native5,
                    R.drawable.ic_money_gif_5,
                    gameURL
                )
            )
        }
        if (extraAdsModels != null && extraAdsModels.size > 0) {
            val minData = 0
            val maxData = extraAdsModels.size - 1
            val randomData = Random().nextInt(maxData - minData + 1) + minData
            return extraAdsModels[randomData]
        }
        return null
    }

    fun bannerModelRandom(getMoneyActivity: Activity): ExtraAdsModel? {
        val arrayList = ArrayList<ExtraAdsModel>()
        if (predChampURL.isCheckNotEmpty()) {
            //Predchamp
            arrayList.add(
                ExtraAdsModel( //Predchamp
                    getMoneyActivity.getString(R.string.preadchamp_play_game),
                    "Play Sports Prediction Contest & Win Upto 50,000 Coins",
                    "No install required! Predict Now",
                    R.drawable.ic_pre_money_smart_banner1,
                    R.drawable.ic_pre_money_gif_2,
                    predChampURL
                )
            )
            arrayList.add(
                ExtraAdsModel( //Predchamp2
                    getMoneyActivity.getString(R.string.preadchamp_play_game),
                    "Best Match Prediction game Today",
                    "Predict results & Win Now!",
                    R.drawable.ic_pre_money_smart_banner2,
                    R.drawable.ic_pre_money_gif_4,
                    predChampURL
                )
            )
            arrayList.add(
                ExtraAdsModel( //Predchamp3
                    getMoneyActivity.getString(R.string.preadchamp_play_game),
                    "Play Sports Prediction Contest & Win Upto 50,000 Coins",
                    "No install required! Predict Now",
                    R.drawable.ic_pre_money_smart_banner3,
                    R.drawable.ic_pre_money_gif_2,
                    predChampURL
                )
            )
            arrayList.add(
                ExtraAdsModel( //Predchamp4
                    getMoneyActivity.getString(R.string.preadchamp_play_game),
                    "Best Match Prediction game Today",
                    "Predict results & Win Now!",
                    R.drawable.ic_pre_money_smart_banner4,
                    R.drawable.ic_pre_money_gif_4,
                    predChampURL
                )
            )
            arrayList.add(
                ExtraAdsModel( //Predchamp5
                    getMoneyActivity.getString(R.string.preadchamp_play_game),
                    "Best Match Prediction game Today",
                    "Predict results & Win Now!",
                    R.drawable.ic_pre_money_smart_banner5,
                    R.drawable.ic_pre_money_gif_4,
                    predChampURL
                )
            )
        }
        if (qurekaURL.isCheckNotEmpty()) {

            // Qureka
            arrayList.add(
                ExtraAdsModel( //Qureka1
                    getMoneyActivity.getString(R.string.bitcoin_play_game),
                    "Aaj Math Quiz khela kya?",
                    "Play and win coins now",
                    R.drawable.ic_extra_smart_banner1,
                    R.drawable.ic_extra_gif_1,
                    qurekaURL
                )
            )
            arrayList.add(
                ExtraAdsModel( //Qureka2
                    getMoneyActivity.getString(R.string.bitcoin_play_game),
                    "Time to Win Now!",
                    "Play Bollywood Quizzes  & win coins daily",
                    R.drawable.ic_extra_smart_banner2,
                    R.drawable.ic_extra_gif_2,
                    qurekaURL
                )
            )
            arrayList.add(
                ExtraAdsModel( //Qureka3
                    getMoneyActivity.getString(R.string.bitcoin_play_game),
                    "Pool prize is 50,000 coins",
                    "Sharpen your Cricket knowledge & win now",
                    R.drawable.ic_extra_smart_banner3,
                    R.drawable.ic_extra_gif_3,
                    qurekaURL
                )
            )
        }
        if (gameURL.isCheckNotEmpty()) {
            // MGL
            arrayList.add(
                ExtraAdsModel( // MGL1
                    getMoneyActivity.getString(R.string.play_games),
                    "Play MGL Games",
                    "Earn Upto 1,00,000 Coins Daily",
                    R.drawable.ic_mgl_banner1,
                    R.drawable.ic_money_gif_1,
                    gameURL
                )
            )
            arrayList.add(
                ExtraAdsModel( //MGL2
                    getMoneyActivity.getString(R.string.play_games),
                    "Play Fruit Chop Game",
                    "No install required! Play Game Now",
                    R.drawable.ic_mgl_banner2,
                    R.drawable.ic_money_gif_2,
                    gameURL
                )
            )
            arrayList.add(
                ExtraAdsModel( //MGL3
                    getMoneyActivity.getString(R.string.play_games),
                    "Play Games To Win Coins",
                    "Your chance of winning is high here! Play Now",
                    R.drawable.ic_mgl_banner3,
                    R.drawable.ic_money_gif_3,
                    gameURL
                )
            )
        }
        if (arrayList != null && arrayList.size > 0) {
            val minData = 0
            val maxData = arrayList.size - 1
            val randomData = Random().nextInt(maxData - minData + 1) + minData
            return arrayList[randomData]
        }
        return null
    }

    fun intertialLayout(): Int {
        val predChamp = intArrayOf(
            R.layout.money_interad,
            R.layout.money_interad2,
            R.layout.money_interad3
        )
        val minData = 0
        val maxData = predChamp.size - 1
        val randomData = Random().nextInt(maxData - minData + 1) + minData
        return predChamp[randomData]
    }

    fun buttonText(getMoneyActivity: Activity): String? {
        val strings = ArrayList<String>()
        strings.add(getMoneyActivity.getString(R.string.play_now))
        strings.add(getMoneyActivity.getString(R.string.play_game))
        val minData = 0
        val maxData = strings.size - 1
        val randomData = Random().nextInt(maxData - minData + 1) + minData
        return strings[randomData]
    }

    fun champOnlyGIFImage(): Int {
        val gifArrayList = intArrayOf(
            R.drawable.ic_pre_money_gif_1,
            R.drawable.ic_pre_money_gif_2,
            R.drawable.ic_pre_money_gif_3,
            R.drawable.ic_pre_money_gif_4,
            R.drawable.ic_pre_money_gif_5,
            R.drawable.ic_pre_money_gif_6,
            R.drawable.ic_pre_money_gif_7
        )
        val minData = 0
        val maxData = gifArrayList.size - 1
        val randomData = Random().nextInt(maxData - minData + 1) + minData
        return gifArrayList[randomData]
    }



}