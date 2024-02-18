package com.example.studentnotes

object C {
    val lectures = listOf(
        Lecture("ACM 311", "Görsel Programlama I"),
        Lecture("ACM 331", "Programlama Dillerinin Kavramları"),
        Lecture("ACM 363", "Kablosuz Ağlar ve Mobil İletişim Sistemleri"),
        Lecture("ACM 373", "Betik Dilleri"),
        Lecture("ACM 411", "Bilgi Teknolojilerinin İnsani ve Etik Yönü"),
        Lecture("ACM 413", "Nesne Yönelimli Yazılım Geliştirme"),
        Lecture("ACM 437", "Nesnelerin Interneti"),
        Lecture("ACM 462", "Karar Destek Sistemleri"),
        Lecture("ACM 465", "Yapay Zeka"),
        Lecture("ACM 472", "3 Boyutlu Oyun Tasarımı"),
        Lecture("ACM 475", "Bilişim Yönergeleri"),
        Lecture("ACM 476", "Veri Madenciliği"),
        Lecture("ACM 474", "Bilgi Sistemleri Güvenliği"),
        Lecture("ACM 498", "Bitirme Projesi"),
        Lecture("ACM 421", "Proje Yönetimi"),
        Lecture("ACM 432", "Kurumsal Bilgi Sistemleri"),
        Lecture("ACM 368", "Web Programlama"),
        Lecture("ACM 394", "Staj"),
        Lecture("ACM 412", "Ağ Programlama"),
        Lecture("ACM 369", "İşletim Sistemleri I"),
        Lecture("ACM 431", "Mobil Cihazları Programlama"),
        Lecture("ACM 212", "İleri Veri Tabanı Uygulamaları"),
        Lecture("ACM 312", "MIS"),
        Lecture("ACM 321", "Nesne Yönelimli Programlama"),
        Lecture("ACM 213", "Bilgi Analizi ve Sistem Tasarımı"),
        Lecture("ACM 222", "Yapısal Programlama"),
        Lecture("ACM 361", "Ağ Oluşturma I"),
        Lecture("ACM 365", "İleri Web Tasarımı"),
        Lecture("ACM 262", "Web Tasarımına Giriş")
    )

    val registeredStudents = listOf(
        Student("20191308013", "12345678"),
        Student("20191308016", "12345678"),
        Student("20201308034", "12345678"),
    )

    val defaultComments = listOf(
        Comment("20191308013", "Great App!"),
        Comment("20191308016", "Easy to use, easy to benefit from! Love it!"),
        Comment("20201308034", "A Programming marvel! Cant do without it!"),
    )

    var loggedInStudent = ""
}