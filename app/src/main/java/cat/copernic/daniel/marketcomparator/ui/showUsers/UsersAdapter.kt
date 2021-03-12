package cat.copernic.daniel.marketcomparator.ui.showUsers

import android.app.PendingIntent.getActivity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.view.isVisible
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.daniel.marketcomparator.R
import cat.copernic.daniel.marketcomparator.domain.data.network.Repo
import cat.copernic.daniel.marketcomparator.model.UsuariDTO

class UsersAdapter(private val context: Context) :
    RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    private var dataList = mutableListOf<UsuariDTO>()
    private val repo = Repo()
    fun setListData(data: MutableList<UsuariDTO>) {
        dataList = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsersAdapter.UsersViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
        return UsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersAdapter.UsersViewHolder, position: Int) {
        val user = dataList[position]
        holder.bindView(user)
    }

    override fun getItemCount(): Int {
        if (dataList.size > 0) {
            return dataList.size
        } else {
            return 0
        }
    }

    inner class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(user: UsuariDTO) {
            itemView.animation = AnimationUtils.loadAnimation(context,R.anim.fade_transition_animation_up_tp_down)
            itemView.findViewById<TextView>(R.id.mailTV).text = user.mail
            itemView.findViewById<TextView>(R.id.usernameTV).text = user.nomUsuari
            var mostrar: Boolean = false
            itemView.findViewById<ImageView>(R.id.btnModify).setOnClickListener {
                if(mostrar == false){
                    itemView.findViewById<Button>(R.id.btnModify1).isVisible = true
                    itemView.findViewById<EditText>(R.id.edPermisos).isVisible = true
                    mostrar = true
                }else{
                    itemView.findViewById<Button>(R.id.btnModify1).isVisible = false
                    itemView.findViewById<EditText>(R.id.edPermisos).isVisible = false
                    mostrar = false
                }


            }
            itemView.findViewById<ImageView>(R.id.btnDelete).setOnClickListener {
                Toast.makeText(context, "Delete " + user.nomUsuari, Toast.LENGTH_SHORT).show()
                repo.removeUser(user.uid)
                dataList.remove(user)
                notifyDataSetChanged()
            }

            itemView.findViewById<Button>(R.id.btnModify1).setOnClickListener {
                user.permisos = itemView.findViewById<EditText>(R.id.edPermisos).text.toString()
                repo.modifyUser(user)
                itemView.findViewById<Button>(R.id.btnModify1).isVisible = false
                itemView.findViewById<EditText>(R.id.edPermisos).isVisible = false
            }

        }

    }
}