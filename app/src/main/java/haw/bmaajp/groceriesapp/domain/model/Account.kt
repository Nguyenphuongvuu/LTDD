package haw.bmaajp.groceriesapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TaiKhoan")
data class Account(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "I_id_tai_khoan")
    val id: Int,
    @ColumnInfo(name = "T_ten_tai_khoan")
    val userName: String,
    @ColumnInfo(name = "T_mat_khau")
    val password: String,
    @ColumnInfo(name = "T_email")
    val email: String,
    @ColumnInfo(name = "I_loai_tai_khoan")
    val type: Int,
)