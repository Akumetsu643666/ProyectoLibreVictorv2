package com.example.proyectolibrevictor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders

class JuegoOfertaAdapter(
    private var ofertas: List<JuegoOferta>,
    private val onItemClick: (JuegoOferta) -> Unit
) : RecyclerView.Adapter<JuegoOfertaAdapter.OfertaViewHolder>() {

    class OfertaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPortada: ImageView = itemView.findViewById(R.id.ivPortadaOferta)
        val tvTitulo: TextView = itemView.findViewById(R.id.tvTituloOferta)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecioOferta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfertaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_juego_oferta, parent, false)
        return OfertaViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfertaViewHolder, position: Int) {
        val oferta = ofertas[position]
        holder.tvTitulo.text = oferta.title
        holder.tvPrecio.text = "Oferta: $${oferta.salePrice} (Antes: $${oferta.normalPrice})"

        val rawUrl = oferta.thumb
        val imageUrl = when {
            rawUrl.isNullOrEmpty() -> null
            rawUrl.startsWith("//") -> "https:$rawUrl"
            else -> rawUrl
        }

        if (!imageUrl.isNullOrEmpty()) {
            val glideUrl = GlideUrl(
                imageUrl,
                LazyHeaders.Builder()
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36")
                    .build()
            )

            Glide.with(holder.itemView.context)
                .load(glideUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(holder.ivPortada)
        } else {
            holder.ivPortada.setImageResource(android.R.drawable.ic_menu_report_image)
        }

        holder.itemView.setOnClickListener {
            onItemClick(oferta)
        }
    }

    override fun getItemCount(): Int = ofertas.size

    fun actualizarLista(nuevasOfertas: List<JuegoOferta>) {
        ofertas = nuevasOfertas
        notifyDataSetChanged()
    }
}