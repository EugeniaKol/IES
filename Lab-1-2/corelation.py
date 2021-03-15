import random
import math
import matplotlib.pyplot as plt
import time

n = 8
tics = 1024
max_amp = 1200

delta_amp = max_amp/n
freqs = []

for i in range(1, n + 1):
    freq = delta_amp * i
    freqs.append(freq) 


def generate_params():
    amps = []
    phases = []
    for i in range(n):
        phase = []
        amp = []
        for j in range(tics):
            amp.append(random.random())
            phase.append(random.random())
        amps.append(amp)
        phases.append(phase)
    return {"amps": amps, "phases": phases}    


def generate_signal():
    res = generate_params()
    amps = res["amps"]
    phases = res["phases"]
    
    signal_sum = []
    for t in range(tics):
        signals = []
        sum = 0
        for j in range(n):
            signal = (amps[j][t])* math.sin(freqs[j]*t+ phases[j][t])
            signals.append(signal)
        for s in signals:
            sum = sum + s
        
        signal_sum.append(sum)
    return signal_sum

signal = generate_signal()
signal_copy = generate_signal()

#functions for plots

def plot_signals():
    plt.plot(range(1, tics + 1), signal, color = '#F67280')
    plt.plot(range(1, tics + 1), signal_copy, color = '#6C5B7B')
    plt.xlabel('час - дискретні відліки') 
    plt.ylabel('сигнал')
    plt.title('Графік залежності згенерованого сигналу від часу') 
    plt.legend(["сигнал", "копія сигналу"])
    plt.show()

def plot_cov(c, taus, is_auto):
    plt.xlabel('tau') 
    plt.ylabel('кореляція')
    if is_auto:
        plt.title('Графік залежності автокореляції від tau')
        color = '#4E9C81'
    else:
        plt.title('Графік залежності кореляції від tau')
        color = '#007373'
    plt.plot(range(taus), c, color)
    plt.show()

#functions for calculation

def calc_avg(x):
    m = 0
    for s in x:
        m = m + s
    return m/tics

def calc_dev(x):
    d = 0
    m = calc_avg(x)
    for s in x:
        d = d + math.pow(s - m, 2)
    return d/tics

def calc_covariation(x, y, tau):
    cov = 0
    mx = calc_avg(x)
    my = calc_avg(y)
    for i in range(max_tic + 1):   #для обчислення кореляції цикл спочатку проходиться по N, тобто max_tic точкам
        val = (x[i] - mx)*(y[i + tau] - my)
        cov += val
    cov = cov/(max_tic - 1)    #отримане значення ділиться на N - 1, тобто max_tic - 1 (відповідно до формули)
    return cov

#setting parameters

#max_tic - кількість значень (точок, що відповідають дискретним відлікам), для яких обчислюється кореляція у функції calc_covariation.
#Це значення менше за кількість згенерованих точок, тому що під час "зсуву" даних на tau кількість нових даних скорочується.
#Наприклад, у даному випадку для обчислення кореляції використовуються значення 600-а точок 
#
#max_tau - максимальний випробувальний інтервал, для якого обчислюється кореляція 
#(кореляція обчислюється для значень tau від 0 до max_tau за допомогою циклу)
max_tic = 600   
max_tau = 250   

#checking if calculation is right
print("corelation: ", calc_covariation(signal, signal, 0))
print("deviation: ", calc_dev(signal))

#main part
#iterating through tau's and calculating correlation

def build_auto_cor():
    covs = []
    for tau in range(max_tau + 1):  #кореляція обчислюється для значень tau від 0 до max_tau за допомогою циклу(1)
        covs.append(calc_covariation(signal, signal, tau))
    
    plot_cov(covs, max_tau + 1, True)

def build_cor():
    covs = []
    for tau in range(max_tau + 1):  #кореляція обчислюється для значень tau від 0 до max_tau за допомогою циклу(2)
        covs.append(calc_covariation(signal, signal_copy, tau))
    
    plot_cov(covs, max_tau + 1, False)

plot_cov()
build_auto_cor()
build_cor()