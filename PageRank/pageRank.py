import math
import numpy as np
import networkx as nx
import matplotlib.pyplot as plt
import pandas as pd
from bokeh.plotting import figure, output_file, show
from bokeh.models import Plot, Range1d, MultiLine, Circle, HoverTool, BoxZoomTool, ResetTool
from bokeh.models.graphs import from_networkx
from bokeh.transform import cumsum
from bokeh.palettes import Category20c,Spectral4
import streamlit as st


def plot_graph(edges):
    G = nx.DiGraph()
    for edge in edges:
        G.add_edge(edge[0], edge[1])
    nx.draw(G, with_labels=True)
    plt.show()

def mypagerank(edges):
    nodes = []
    for edge in edges:
        if edge[0] not in nodes:
            nodes.append(edge[0])
        if edge[1] not in nodes:
            nodes.append(edge[1])
    print(nodes)
    N = len(nodes)
    i = 0
    node_to_num = {}
    for node in nodes:
        node_to_num[node] = i
        i += 1
    for edge in edges:
        edge[0] = node_to_num[edge[0]]
        edge[1] = node_to_num[edge[1]]
    print(edges)

    S = np.zeros([N, N])
    for edge in edges:
        S[edge[1], edge[0]] = 1
    print(S)

    for j in range(N):
        sum_of_col = sum(S[:, j])
        for i in range(N):
            if sum_of_col != 0:
                S[i, j] /= sum_of_col
            else:
                S[i, j] = 1 / N
    print(S)

    alpha = 0.85
    A = alpha * S + (1 - alpha) / N * np.ones([N, N])
    print(A)

    # 生成初始的PageRank值，记录在P_n中，P_n和P_n1均用于迭代
    P_n = np.ones(N) / N
    P_n1 = np.zeros(N)

    e = 100000  # 误差初始化
    k = 0  # 记录迭代次数
    print('loop...')

    while e > 0.00000001:  # 开始迭代
        P_n1 = np.dot(A, P_n)  # 迭代公式
        e = P_n1 - P_n
        e = max(map(abs, e))  # 计算误差
        P_n = P_n1
        k += 1
        print('iteration %s:' % str(k), P_n1)
    print('final result:', P_n)
    print('P_n length',len(P_n))
    return P_n



def plot_values(df):
    df.plot(kind='bar',colormap='gist_rainbow',title="PageRank calculated access probability of each web page")
    plt.xticks(range(len(nodes)),nodes)
    plt.show()


def bokeh_plot(df):
    xdata = df['节点']
    ydata = df['访问概率']
    p = figure(x_range=xdata, plot_height=350)

    # p = figure(x_range=xdata, plot_height=350,title="bokeh Visualization results",x_axis_label="web index",y_axis_label='Access probability')
    p.vbar(x=xdata, top=ydata, width=0.9)
    p.line(range(len(xdata)),ydata, legend_label="Temp.", line_width=2)
    output_file('./各网址访问概率.html')
    show(p)

def bokeh_plot2(edges):
    G = nx.DiGraph()
    for edge in edges:
        G.add_edge(edge[0], edge[1])
    SAME_CLUB_COLOR, DIFFERENT_CLUB_COLOR = "black", "red"
    edge_attrs = {}
    for start_node, end_node, _ in G.edges(data=True):
        edge_color = SAME_CLUB_COLOR if G.nodes[start_node]== G.nodes[end_node] else DIFFERENT_CLUB_COLOR
        edge_attrs[(start_node, end_node)] = edge_color
    nx.set_edge_attributes(G, edge_attrs, "edge_color")
    # Show with Bokeh
    plot = Plot(plot_width=400, plot_height=400,
                x_range=Range1d(-1.1, 1.1), y_range=Range1d(-1.1, 1.1))
    plot.title.text = "Graph Interaction Demonstration"
    node_hover_tool = HoverTool(tooltips=[("index", "@index"), ("club", "@club")])
    plot.add_tools(node_hover_tool, BoxZoomTool(), ResetTool())
    graph_renderer = from_networkx(G, nx.spring_layout, scale=1, center=(0, 0))
    graph_renderer.node_renderer.glyph = Circle(size=15, fill_color=Spectral4[0])
    graph_renderer.edge_renderer.glyph = MultiLine(line_color="edge_color", line_alpha=0.8, line_width=1)
    plot.renderers.append(graph_renderer)
    output_file("网络关系图.html")
    show(plot)


def bokeh_plot3(nodes,res):
    output_file("pie.html")
    x=dict(zip(nodes,res))
    data = pd.Series(x).reset_index(name='value').rename(columns={'index': 'country'})
    data['angle'] = data['value'] / data['value'].sum() * 2 * math.pi
    data['color'] = Category20c[len(x)]
    p = figure(plot_height=350, title="Pie Chart",tooltips="@country: @value", x_range=(-0.5, 1.0))
    p.wedge(x=0, y=1, radius=0.4,
            start_angle=cumsum('angle', include_zero=True), end_angle=cumsum('angle'),
            line_color="white", fill_color='color', legend_field='country', source=data)
    show(p)

if __name__ == '__main__':
    # 读入有向图，存储边
    f = open('WikiData.txt', 'r')
    edges = [line.strip('\n').split('\t') for line in f]
    # print(edges)
    plot_graph(edges)
    res = mypagerank(edges)
    nodes = []
    for i in range(105):
        nodes.append(i)
    # nodes = ['A','B','C','D','E','F','G','H','I','J','K']
    data = {"节点":nodes,"访问概率":res}
    df = pd.DataFrame(data)
    plot_values(df)
    # bokeh_plot(df)
    # bokeh_plot2(edges)
    # bokeh_plot3(nodes,res)
    # st.title("Use streamlit show the resoult of pageRank")
    # # st.pyplot()
    #
    # if st.button("import url data"):
    #     st.write('import data success')
    # else:
    #     st.write('import data fail')
    # st.write("parameter control")
    # # teleport = 0
    # # teleport = st.sidebar('teleport')
    # # st.write("when parameter of teleport is " ,teleport ,)
    # x = st.slider('teleport')
    # st.write("when parameter of teleport is ", x, "the result is below")
    # chart_data = pd.DataFrame(
    # np.random.randn(50, 3),
    # columns = ["a", "b", "c"])
    # st.bar_chart(chart_data)