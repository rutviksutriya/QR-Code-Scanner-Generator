package com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model

data class GetMoneyModel(
    val ResponseCode: Int,
    val ResponseMsg: String = "",
    val Result: String = "",
    val ServerTime: String = "",
    val ad_priority: NewAdPriority,
    val data: NewAdsData
)

data class NewAdPriority(
    val ads: List<Ad> = arrayListOf(),
    val priority: String = ""
)

data class Ad(
    val acc_id: String = "",
    val acc_type: String = "",
    val add_size: String = "",
    val add_type: String = "",
    var id: String = "",
    var key: String = ""
)

data class NewAdsData(
    val app_url: String = "",
    val app_node_url: String = "",
    val is_show_second: Boolean = false,
    val is_loader: String = "",
    val inter_mill_sec: Int = 0,
    val is_back_press_count: String = "",
    val bitcoin_url: String = "",
    val is_withdrawal: String = "",
    val app_version_code: String? = "",
    val developer_name: String = "",
    val game_url: String = "",
    val is_user_dialog: Boolean = false,
    val is_normal_ad_count: String = "",
    val is_advertise_available: String = "",
    val is_info_available: String = "",
    val is_qureka: String = "",
    val predchamp_url: String = "",
    val user_dialog_update_text: String = "",
    val rate_text: String = "",

    val is_on_resume: Boolean = false,
    val is_splash_available: String = "",
    val user_dialog_url: String = "",
    val user_dialog_text: String = "",
    val user_dialog_img: String = "",
    val usr_dialog_btn: String = ""
)