package br.com.elpe.e_macro

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter


class QrCode {
    private val nameQrCode = "qrcodeimage"
    private val kindQrCode = "png"

    fun encode(path: String,str: String): Bitmap {

            val qrCodeWriter: QRCodeWriter = QRCodeWriter()
            val bitMatrix = qrCodeWriter.encode(str, BarcodeFormat.QR_CODE, 200, 200)
            val bmp = Bitmap.createBitmap(bitMatrix.width, bitMatrix.height, Bitmap.Config.RGB_565)
            for (x in 0 until bitMatrix.width) {
                for (y in 0 until  bitMatrix.height)
                    bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
            return bmp

        //return Bitmap
    }

    private fun verifyStr(str: String): Boolean {
        return str.isNotEmpty()
    }
}