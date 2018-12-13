package com.example.debce.projetofilme.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.debce.projetofilme.R
import com.example.debce.projetofilme.model.Filme
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_lista_filme.view.*
import java.text.FieldPosition

class FilmeRecyclerAdapter internal constructor(context: Context) :
        RecyclerView.Adapter<FilmeRecyclerAdapter.ViewHolder>() {

    var onItemClick: ((Filme) -> Unit)? = null
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var filmes  = emptyList<Filme>()

    // infla o layout do item da lista para cada componente da lista
    override fun onCreateViewHolder(holder: ViewGroup, position: Int):
            ViewHolder {
        val view = inflater.inflate(R.layout.item_lista_filme , holder,
                false )
        return ViewHolder(view)
    }

    // retorna o tamanho da lista
    override fun getItemCount() = filmes.size

    // colocando os itens da lista nos itens de view da lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = filmes[position]
        holder.nomeFilme.text = current.fNome
    }

    // classe para mapear os componentes do item da lista
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nomeFilme: TextView = itemView.txtFilmeListaNome

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(filmes[adapterPosition])
            }
        }

    }


    fun setFilmeList(friendList: List<Filme>){
        this.filmes = friendList
        notifyDataSetChanged()
    }


}